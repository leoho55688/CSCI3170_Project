package salesSystem;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db41";
        String dbUsername = "Group41";
        String dbPassword = "CSCI3170";

        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("use db41;");
            System.out.println("Welcome to sales system!");
            menu(conn);
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: Java Mysql DB Driver not found!!!");
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public static void menu (Connection conn) {
        int choice = 0;
        Scanner scan = new Scanner(System.in);

        while (choice != 4) {
            System.out.println("-----Main Menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");

            while (true) {
                try {
                    System.out.println("Enter Your Choice:");
                    choice = Integer.parseInt(scan.nextLine());
                    if (choice < 1 || choice > 4)
                        throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("[ERROR] Invalid input.");
                }
            }

            switch (choice) {
                case 1:
                    SystemAdministrator admin = new SystemAdministrator(conn);
                    admin_menu(scan, admin);
                    break;
                // case 2:
                //     Salesperson salesperson = new Salesperson();
                //     salesperson_menu(scan, salesperson, conn);
                //     break;
                // case 3:
                //     Manager manager = new Manager(conn);
                //     manager_menu(manager, conn);
                //     break;
                default:

            }
        }
        scan.close();
    }

    public static void admin_menu (Scanner scan, SystemAdministrator admin) {
        int choice = 0;

        while (choice != 5) {
            System.out.println("-----Operations for administrator menu-----");
            System.out.println("what kinds of operation would you like to perform?");
            System.out.println("1. Create all tables");
            System.out.println("2. Delete all tables");
            System.out.println("3. Load from datafile");
            System.out.println("4. Show content of a table");
            System.out.println("5. Return to the main menu");

            while (true) {
                try {
                    System.out.println("Enter your choice:");
                    choice = Integer.parseInt(scan.nextLine());
                    if (choice < 1 || choice > 5)
                        throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("[ERROR] Invalid input.");
                }
            }

            switch (choice) {
                case 1:
                    admin.createTables();
                    break;
                case 2:
                    admin.deleteTables();
                    break;
                case 3:
                    admin.loadData();
                    break;
                case 4:
                    admin.showData();
                    break;
                default:
            }
        }
    }

    public static void salesperson_menu (Scanner scan, Salesperson salesperson) {

    }
    public static void manager_menu (Manager manager) {
        
    }
}