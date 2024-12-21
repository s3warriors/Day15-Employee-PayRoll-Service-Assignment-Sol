import java.io.File;
import java.io.IOException;
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


}
