import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeePayrollService {
    private static final String PAYROLL_FILE_NAME = "employeePayroll.txt";

    private ArrayList<EmployeePayroll> employees = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        EmployeePayrollService service = new EmployeePayrollService();

        // UC 1: Read and Write Employee Payroll to Console
        service.readEmployeeFromConsole();
        service.writeEmployeeToConsole();
        // UC 2: Demonstrate File Operations
//        service.performFileOperations();
        // UC 3: Create a Watch Service for a Directory
        service.watchDirectory("testDirectory");
        // UC 4: Write Employee Payroll to File
        service.writeEmployeeToFile();

        // UC 5: Print and Count Employee Payroll Entries
        service.printEmployeeFromFile();
        service.countEntriesInFile();

        // UC 6: Perform Analysis on Employee Payroll File
        service.analyzeEmployeePayrollFile();
   }



    // UC 1: Read and Write Employee Payroll to Console
    private void readEmployeeFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee Details (ID, Name, Salary):");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Salary: ");
        double salary = scanner.nextDouble();

        employees.add(new EmployeePayroll(id, name, salary));
        System.out.println("Employee added.");
    }

    private void writeEmployeeToConsole() {
        System.out.println("\nEmployee Payroll Details:");
        for (EmployeePayroll employee : employees) {
            System.out.println(employee);
        }
    }

    // UC 2: Demonstrate File Operations
    private void performFileOperations() throws IOException {
        File file = new File(PAYROLL_FILE_NAME);

        // Check if file exists
        if (file.exists()) {
            System.out.println("File exists.");
            // Delete file
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            }
        } else {
            System.out.println("File does not exist.");
        }

        // Create a directory
        File directory = new File("testDirectory");
        if (directory.mkdir()) {
            System.out.println("Directory created.");
        }

        // Create an empty file
        if (file.createNewFile()) {
            System.out.println("Empty file created.");
        }

        // List files and directories
        File currentDir = new File(".");
        String[] files = currentDir.list();
        System.out.println("Files and Directories:");
        for (String fileName : files) {
            System.out.println(fileName);
        }
    }


    // UC 3: Watch Service for a Directory
    private void watchDirectory(String directoryPath) throws IOException, InterruptedException {
        Path path = Paths.get(directoryPath);
        WatchService watchService = FileSystems.getDefault().newWatchService();

        // Register the directory with the WatchService for CREATE, DELETE, and MODIFY events
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Watching directory: " + path);
        System.out.println("Type 'exit' to stop watching.");

        // Start a thread to monitor user input for termination
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                if (scanner.nextLine().equalsIgnoreCase("exit")) {
                    try {
                        watchService.close(); // Close the WatchService when "exit" is typed
                        System.out.println("Watch service stopped.");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            scanner.close();
        });
        inputThread.start();

        try {
            // Monitor the directory for events
            while (true) {
                WatchKey key = watchService.take(); // Blocks until an event occurs

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind(); // Type of event
                    Path fileName = (Path) event.context(); // Affected file or directory

                    // Print details of the event
                    System.out.println(kind + ": " + fileName);
                }

                // Reset the key to continue watching
                if (!key.reset()) {
                    System.out.println("WatchKey is no longer valid. Exiting.");
                    break; // Exit loop if directory is no longer accessible
                }
            }
        } catch (ClosedWatchServiceException e) {
            System.out.println("Watch service has been closed.");
        }
    }

    // UC 4: Write Employee Payroll to File
    private void writeEmployeeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYROLL_FILE_NAME, true))) { // 'true' enables append mode
            for (EmployeePayroll employee : employees) {
                writer.append(employee.toString());
                writer.newLine();
            }
            System.out.println("Employee Payroll appended to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // UC 5: Print and Count Employee Payroll Entries
    private void printEmployeeFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PAYROLL_FILE_NAME))) {
            String line;
            System.out.println("Employee Payrolls from File:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void countEntriesInFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PAYROLL_FILE_NAME))) {
            int count = 0;
            while (reader.readLine() != null) {
                count++;
            }
            System.out.println("Total entries in file: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // UC 6: Perform Analysis on Employee Payroll File
    private void analyzeEmployeePayrollFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PAYROLL_FILE_NAME))) {
            int totalEntries = 0;
            double totalSalary = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                totalEntries++;
                String[] details = line.split(", ");
                String salaryString = details[2].split(": ")[1];
                totalSalary += Double.parseDouble(salaryString);
            }

            System.out.println("Total Employees: " + totalEntries);
            System.out.println("Total Salary: " + totalSalary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

