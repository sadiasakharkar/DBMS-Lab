package JDBC_Project;

import java.sql.*;
import java.util.Scanner;

public class CreateDB {

    static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root"; 
    static final String PASS = "Sadia@700"; 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to MySQL ✅");

            while (true) {
                System.out.println("\nMENU");
                System.out.println("1. Insert");
                System.out.println("2. Display");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int ch = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (ch) {
                    case 1: insertEmp(con, sc); break;
                    case 2: displayEmp(con); break;
                    case 3: updateEmp(con, sc); break;
                    case 4: deleteEmp(con, sc); break;
                    case 5:
                        con.close();
                        System.out.println("Exited. Bye 👋");
                        System.exit(0);
                    default: System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert Employee
    static void insertEmp(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter empno: ");
        int empno = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter name: ");
        String ename = sc.nextLine();
        System.out.print("Enter job: ");
        String job = sc.nextLine();
        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();
        System.out.print("Enter commission: ");
        double commission = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter date_of_joining (YYYY-MM-DD): ");
        String doj = sc.nextLine();
        System.out.print("Enter dept_no: ");
        int dept_no = sc.nextInt();

        String query = "INSERT INTO emp VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, empno);
        ps.setString(2, ename);
        ps.setString(3, job);
        ps.setDouble(4, salary);
        ps.setDouble(5, commission);
        ps.setDate(6, Date.valueOf(doj));
        ps.setInt(7, dept_no); 
        ps.executeUpdate();
        System.out.println("Employee inserted successfully ✅");
    }

    // Display Employees
    static void displayEmp(Connection con) throws SQLException {
        String query = "SELECT * FROM emp";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("\n--- Employee Records ---");
        while (rs.next()) {
            System.out.println(rs.getInt("empno") + " | " +
                               rs.getString("ename") + " | " +
                               rs.getString("job") + " | " +
                               rs.getDouble("salary") + " | " +
                               rs.getDouble("commission") + " | " +
                               rs.getDate("date_of_joining") + " | " +
                               rs.getInt("dept_no")); 
        }
    }

    // Update Employee - flexible menu
    static void updateEmp(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter empno to update: ");
        int empno = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("What do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Job");
        System.out.println("3. Salary");
        System.out.println("4. Commission");
        System.out.println("5. Date of Joining");
        System.out.println("6. Dept No");
        System.out.print("Enter your choice: ");
        int field = sc.nextInt();
        sc.nextLine(); // consume newline

        PreparedStatement ps = null;
        switch (field) {
            case 1:
                System.out.print("Enter new name: ");
                String name = sc.nextLine();
                ps = con.prepareStatement("UPDATE emp SET ename=? WHERE empno=?");
                ps.setString(1, name);
                ps.setInt(2, empno);
                break;
            case 2:
                System.out.print("Enter new job: ");
                String job = sc.nextLine();
                ps = con.prepareStatement("UPDATE emp SET job=? WHERE empno=?");
                ps.setString(1, job);
                ps.setInt(2, empno);
                break;
            case 3:
                System.out.print("Enter new salary: ");
                double salary = sc.nextDouble();
                ps = con.prepareStatement("UPDATE emp SET salary=? WHERE empno=?");
                ps.setDouble(1, salary);
                ps.setInt(2, empno);
                break;
            case 4:
                System.out.print("Enter new commission: ");
                double commission = sc.nextDouble();
                ps = con.prepareStatement("UPDATE emp SET commission=? WHERE empno=?");
                ps.setDouble(1, commission);
                ps.setInt(2, empno);
                break;
            case 5:
                System.out.print("Enter new date_of_joining (YYYY-MM-DD): ");
                String doj = sc.nextLine();
                ps = con.prepareStatement("UPDATE emp SET date_of_joining=? WHERE empno=?");
                ps.setDate(1, Date.valueOf(doj));
                ps.setInt(2, empno);
                break;
            case 6:
                System.out.print("Enter new dept_no: ");
                int dept = sc.nextInt(); 
                ps = con.prepareStatement("UPDATE emp SET dept_no=? WHERE empno=?");
                ps.setInt(1, dept);  
                ps.setInt(2, empno);
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? "Updated successfully" : "Employee not found️");
    }

    // Delete Employee
    static void deleteEmp(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter empno to delete: ");
        int empno = sc.nextInt();
        sc.nextLine();

        String query = "DELETE FROM emp WHERE empno=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, empno);

        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? "Deleted successfully " : "Employee not found ️");
    }
}
