import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeePayrollService {
    private ArrayList<EmployeePayroll> employees = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        EmployeePayrollService service = new EmployeePayrollService();

        // UC 1: Read and Write Employee Payroll to Console
        service.readEmployeeFromConsole();
        service.writeEmployeeToConsole();
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


}
