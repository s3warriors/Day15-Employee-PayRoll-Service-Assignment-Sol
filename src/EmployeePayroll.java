 class EmployeePayroll {
        int id;
        String name;
        double salary;

        public EmployeePayroll(int id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee ID: " + id + ", Name: " + name + ", Salary: " + salary;
        }
    }