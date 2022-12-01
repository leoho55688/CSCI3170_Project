package salesSystem;

import java.sql.*;

public class SystemAdministrator {
        private Connection con;

        public SystemAdministrator(Connection c){
            con = c;
        }

        public static void createTables(){
            System.out.print(s: "Processing...");

            try{
                PreparedStatement stmt;
                String sqlcode;

                sqlcode = "create table if not exists category" +
                "(cID integer primary key check(length(cID)=1)," +
                "cName char(20) not null)";
                stmt = con.prepareStatement(sqlcode);
                stmt.executeUpdate();

                sqlcode = "create table if not exists manufacturer" +
                "(mID integer primary key check(length(mID)<=2)," +
                "mName char(20) not null" +
                "mAddress char(50) not null" +
                "mPhoneNumber integer not null check(length(mPhoneNumber)=8))";
                stmt = con.prepareStatement(sqlcode);
                stmt.executeUpdate();

                sqlcode = "create table if not exists part" +
                "(pID integer primary key check(length(pID)<=3)," +
                "pName char(20) not null" +
                "pPrice integer not null check(length(pPrice)<=5)" +
                "foreign key(mID) references manufacturer(mID)," +
                "foreign key(cID) references category(cID)," +
                "pWarrantyPeriod integer not null check(length(pWarrantyPeriod)<=2)" + 
                "pAvailableQuantity integer not null check(length(pAvailableQuantity)<=2))";
                stmt = con.prepareStatement(sqlcode);
                stmt.executeUpdate();

                sqlcode = "create table if not exists salesperson" +
                "(sID integer primary key check(length(sID)<=2)," +
                "sName char(20) not null" +
                "sAddress char(50) not null" +
                "sPhoneNumber integer not null check(length(sPhoneNumber)=8)" +
                "sExperience integer not null check(length(sExperience)=1))";
                stmt = con.prepareStatement(sqlcode);
                stmt.executeUpdate();

                sqlcode = "create table if not exists transaction" +
                "(tID integer primary key check(length(tID)<=4)," +
                "foreign key(pID) references part(pID)," +
                "foreign key(sID) references salesperson(sID)," +
                "tDate date not null)";
    
                stmt = con.prepareStatement(sqlcode);
                stmt.executeUpdate();

                System.out.println(x: "Done! Database is initialized!");
            }catch(Exception e){
                System.out.println("\n"+e);
            }
        }

        public static void deleteTables(){

        }

        public static void loadData(){

        }

        public static void showData(){

        }
    
}