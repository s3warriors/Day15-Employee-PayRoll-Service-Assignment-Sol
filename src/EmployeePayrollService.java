import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        service.performFileOperations();
        // UC 3: Create a Watch Service for a Directory
//        service.watchDirectory("testDirectory");
        // UC 4: Write Employee Payroll to File
        service.writeEmployeeToFile();
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

        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Watching directory: " + path);

        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                Path fileName = (Path) event.context();
                System.out.println(kind + ": " + fileName);
            }
            if (!key.reset()) {
                break;
            }
        }
    }
    // UC 4: Write Employee Payroll to File
    private void writeEmployeeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYROLL_FILE_NAME))) {
            for (EmployeePayroll employee : employees) {
                writer.write(employee.toString());
                writer.newLine();
            }
            System.out.println("Employee Payroll written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
