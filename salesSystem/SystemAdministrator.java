package salesSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class SystemAdministrator {
    private Connection conn;

    public SystemAdministrator (Connection c) {
        conn = c;
    }

    public void createTables () {
        System.out.print("Processing...");

        try{
            PreparedStatement stmt;
            String sqlcode;

            sqlcode = "create table if not exists category" +
                    "(cID integer primary key check(length(cID)=1)," +
                    "cName char(20) not null)";
            stmt = conn.prepareStatement(sqlcode);
            stmt.executeUpdate();

            sqlcode = "create table if not exists manufacturer" +
                    "(mID integer primary key check(length(mID)<=2)," +
                    "mName char(20) not null," +
                    "mAddress char(50) not null," +
                    "mPhoneNumber integer not null check(length(mPhoneNumber)=8))";
            stmt = conn.prepareStatement(sqlcode);
            stmt.executeUpdate();

            sqlcode = "create table if not exists part" +
                    "(pID integer primary key check(length(pID)<=3)," +
                    "pName char(20) not null," +
                    "pPrice integer not null check(length(pPrice)<=5)," +
		    "mID int," +
                    "cID int," +
                    "pWarrantyPeriod integer not null check(length(pWarrantyPeriod)<=2)," +
                    "pAvailableQuantity integer not null check(length(pAvailableQuantity)<=2)," +
                    "foreign key(cID) references category(cID)," +
                    "foreign key(mID) references manufacturer(mID))";
            stmt = conn.prepareStatement(sqlcode);
            stmt.executeUpdate();

            sqlcode = "create table if not exists salesperson" +
                    "(sID integer primary key check(length(sID)<=2)," +
                    "sName char(20) not null," +
                    "sAddress char(50) not null," +
                    "sPhoneNumber integer not null check(length(sPhoneNumber)=8)," +
                    "sExperience integer not null check(length(sExperience)=1))";
            stmt = conn.prepareStatement(sqlcode);
            stmt.executeUpdate();

            sqlcode = "create table if not exists transaction" +
                    "(tID integer primary key check(length(tID)<=4)," +
                    "pID int," +
                    "sID int," +
                    "tDate date not null," +
                    "foreign key(pID) references part(pID)," +
                    "foreign key(sID) references salesperson(sID))";

            stmt = conn.prepareStatement(sqlcode);
            stmt.executeUpdate();

            System.out.println("Done! Database is initialized!");
        }catch(Exception e){
            System.out.println("\n"+e);
        }
    }

    public void deleteTables () {
			System.out.print("Processing...");

            try {
                String sqlcode = "DROP TABLE IF EXISTS ";
                PreparedStatement stmt;

                String tables[] = {"category", "manufacturer", "part", "salesperson", "transaction"};
                
                for(int i = tables.length - 1; i >= 0; i--){
                    stmt = conn.prepareStatement(sqlcode + tables[i]);
                    stmt.execute();
                }

                System.out.println("Done! Database is removed!");
            } catch (Exception e) {
                System.out.println("\n" + e);
            }
    }

    public void loadData () {
        Scanner scan = new Scanner(System.in); // do not close

        while(true) {
            try {
                System.out.println("Please enter the folder path");
                String path = scan.nextLine();
                System.out.print("Processing...");

                loadCategorys(path, conn);
                loadManufacturers(path, conn);
                loadParts(path, conn);
                loadSalespersons(path, conn);
                loadTransactions(path, conn);

                System.out.println("Data is loaded!");
                break;
            } catch (FileNotFoundException fe) {
                System.out.println("\n[ERROR] Invalid folder path.");
            } catch (Exception e) {
		System.out.println(e);
                System.out.println("\n[ERROR] Tables does not exist or files already loaded.");
                break;
            }
        }


    }

    public void checkData () throws SQLException{
		Scanner scan = new Scanner(System.in);
            String table;

            while(true){
                try{
                    System.out.print("Which table would you like to show: ");
                    table = scan.nextLine();
                    if(!((table.equals("category"))||(table.equals("manufacturer"))||(table.equals("part"))||(table.equals("salesperson"))||(table.equals("transaction"))))
                        throw new Exception();
                    break;
                } catch (Exception e){
                    System.out.println("Table not found. Please enter again.");
                }
            }

            System.out.println("Content of table " + table + ":");
			String queryTable = "select * from " + table;
            PreparedStatement stmt = conn.prepareStatement(queryTable);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
				System.out.println("There is no data in this table.");
				return;
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			System.out.print("| ");
            for (int i = 1; i <= columnsNumber; i++) {
				String columnName = rsmd.getColumnName(i);
				System.out.print(columnName + " | ");
			}
			System.out.println("");
			do {
				System.out.print("| ");
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " | ");
				}
				System.out.println("");
			} while (rs.next());
			
	}

    private void loadCategorys (String path, Connection conn) throws IOException, SQLException {
        BufferedReader txt = new BufferedReader(new FileReader(path + "/category.txt"));
        String line;
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO category VALUES (?, ?)");

        while ((line = txt.readLine()) != null) {
            String data[] = line.split("\\t");
            for (int i = 1; i <= data.length; i++) {
                pstmt.setString(i, data[i - 1]);
            }
            pstmt.execute();
        }
        txt.close();
    }
    private void loadManufacturers (String path, Connection conn) throws IOException, SQLException {
        BufferedReader txt = new BufferedReader(new FileReader(path + "/manufacturer.txt"));
        String line;
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO manufacturer VALUES (?, ?, ?, ?)");

        while ((line = txt.readLine()) != null) {
            String data[] = line.split("\\t");
            for (int i = 1; i <= data.length; i++) {
                pstmt.setString(i, data[i - 1]);
            }
            pstmt.execute();
        }
        txt.close();
    }
    private void loadParts (String path, Connection conn) throws IOException, SQLException {
        BufferedReader txt = new BufferedReader(new FileReader(path + "/part.txt"));
        String line;
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO part VALUES (?, ?, ?, ?, ?, ?, ?)");

        while ((line = txt.readLine()) != null) {
            String data[] = line.split("\\t");
            for (int i = 1; i <= data.length; i++) {
                pstmt.setString(i, data[i - 1]);
            }
            pstmt.execute();
        }
        txt.close();
    }
    private void loadSalespersons (String path, Connection conn) throws IOException, SQLException {
        BufferedReader txt = new BufferedReader(new FileReader(path + "/salesperson.txt"));
        String line;
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO salesperson VALUES (?, ?, ?, ?, ?)");

        while ((line = txt.readLine()) != null) {
            String data[] = line.split("\\t");
            for (int i = 1; i <= data.length; i++) {
                pstmt.setString(i, data[i - 1]);
            }
            pstmt.execute();
        }
        txt.close();
    }
    private void loadTransactions (String path, Connection conn) throws IOException, SQLException {
        BufferedReader txt = new BufferedReader(new FileReader(path + "/transaction.txt"));
        String line;
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO transaction VALUES (?, ?, ?, ?)");

        while ((line = txt.readLine()) != null) {
            String data[] = line.split("\\t");
            for (int i = 1; i <= data.length; i++) {
				if (i == data.length) {
					String date[] = data[i - 1].split("/");
					data[i - 1] = date[2] + "/" + date[1] + "/" + date[0];
				}
                pstmt.setString(i, data[i - 1]);
            }
            pstmt.execute();
        }
        txt.close();
    }
}
