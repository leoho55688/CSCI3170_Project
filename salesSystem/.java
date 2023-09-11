package salesSystem;

import java.sql.*;
import java.util.*;

public class Salesperson {

private Connection conn;

public Salesperson(Connection c){
    conn = c;
}

public boolean checkKeyword(String keyword, int choice) throws SQLException {

    ResultSet rs = null;
    String statement;
    PreparedStatement prepstmt;
    boolean iskeywordhere;

    if(choice == 1){
        
        statement = "SELECT * FROM part WHERE pName = ?";
        prepstmt = conn.prepareStatement(statement);
        prepstmt.setString(1, keyword);
        rs = prepstmt.executeQuery();
    }

    if(choice == 2){
        statement = "SELECT * FROM manufacturer WHERE mName = ?";
        prepstmt = conn.prepareStatement(statement);
        prepstmt.setString(1, keyword);
        rs = prepstmt.executeQuery();
    }

    if(rs.next()){
        iskeywordhere = true;
    }else{
        iskeywordhere = false;
    }

    return iskeywordhere;
   
}



 public void queryParts() throws SQLException {

    int choice = 0;
    Scanner scan = new Scanner(System.in);
    String keyword; 
    boolean exist_keyword;
    int order = 0;

    while(choice != 1 && choice != 2){
        System.out.println("Choose the Search criterion:");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");

        System.out.println("Choose the search criterion: ");
        choice = Integer.parseInt(scan.nextLine());

        if(choice == 1 || choice == 2){

            System.out.println("Type in the Search Keyword: ");
            keyword = scan.nextLine();
            exist_keyword = checkKeyword(keyword, choice); 
                
            if(exist_keyword == true){
            
            while(order != 1 && order != 2){
                System.out.println("Choose ordering:");
                System.out.println("1. By price, ascending order");
                System.out.println("2. By price, descending order");
                System.out.println("Choose the search criterion: ");
                order = Integer.parseInt(scan.nextLine());

                if(order == 1 || order == 2){
                    ResultSet rs = null;
                    String statement;
                    PreparedStatement prepstmt;

                    if(choice == 1 && order == 1){
                       statement = "SELECT P.pID AS ID, P.pName AS Name, M.mName AS Manufacturer, C.cName AS Category, P.pAvailableQuantity AS Quantity, P.pWarrantyPeriod AS Warranty, P.pPrice AS Price" +  
                       "FROM part P, manufacturer M, category C" +
                       "WHERE C.cID = P.cID" +
                       "AND M.mID = P.mID" + 
                       "AND P.pName = ?" +
                       "ORDER BY P.pPrice ASC";

                       prepstmt = conn.prepareStatement(statement);
                       prepstmt.setString(1,keyword);
                       rs = prepstmt.executeQuery();
                    }

                    if(choice == 1 && order == 2){
                        statement = "SELECT P.pID AS ID, P.pName AS Name, M.mName AS Manufacturer, C.cName AS Category, P.pAvailableQuantity AS Quantity, P.pWarrantyPeriod AS Warranty, P.pPrice AS Price" +  
                        "FROM part P, manufacturer M, category C" +
                        "WHERE C.cID = P.cID" +
                        "AND M.mID = P.mID" + 
                        "AND P.pName = ?" +
                        "ORDER BY P.pPrice DESC"; 

                       prepstmt = conn.prepareStatement(statement);
                       prepstmt.setString(1,keyword);
                       rs = prepstmt.executeQuery();
                    }

                    if(choice == 2 && order == 1){
                        statement = "SELECT P.pID AS ID, P.pName AS Name, M.mName AS Manufacturer, C.cName AS Category, P.pAvailableQuantity AS Quantity, P.pWarrantyPeriod AS Warranty, P.pPrice AS Price" +  
                        "FROM part P, manufacturer M, category C" +
                        "WHERE C.cID = P.cID" +
                        "AND M.mID = P.mID" + 
                        "AND M.mName = ?" +
                        "ORDER BY P.pPrice ASC"; 

                       prepstmt = conn.prepareStatement(statement);
                       prepstmt.setString(1,keyword);
                       rs = prepstmt.executeQuery();
                    }

                    if(choice == 2 && order == 2){
                        statement = "SELECT P.pID AS ID, P.pName AS Name, M.mName AS Manufacturer, C.cName AS Category, P.pAvailableQuantity AS Quantity, P.pWarrantyPeriod AS Warranty, P.pPrice AS Price" +  
                        "FROM part P, manufacturer M, category C" +
                        "WHERE C.cID = P.cID" +
                        "AND M.mID = P.mID" + 
                        "AND M.mName = ?" +
                        "ORDER BY P.pPrice DESC"; 

                       prepstmt = conn.prepareStatement(statement);
                       prepstmt.setString(1,keyword);
                       rs = prepstmt.executeQuery();
                    }

                    }else{
                        System.out.println("Error! Please reinput order again!");
                    }

                    }
                }else{
                    System.out.println("No keyword found!");
                }
            }else{
                System.out.println("[Error] Please reinput choice again!");
            }

                   
             }
 }



public boolean checkpartID(int part_id) throws SQLException {
    
    ResultSet rs = null;
    String statement;
    PreparedStatement prepstmt;
    boolean ispartIDhere = false;

    statement = "SELECT P.pID FROM part P WHERE P.pID = ?";
    prepstmt = conn.prepareStatement(statement);
    prepstmt.setInt(1, part_id);
    rs = prepstmt.executeQuery();

    if(rs.next() == true){
        ispartIDhere = true;
    }else{
        ispartIDhere = false;
    }

    return ispartIDhere;

}

public boolean checksalesID(int salesperson_id) throws SQLException {

    ResultSet rs = null;
    String statement;
    PreparedStatement prepstmt;
    boolean issalesIDhere = false;

    statement = "SELECT S.sID FROM salesperson S WHERE S.sID = ? ";
    prepstmt = conn.prepareStatement(statement);
    prepstmt.setInt(1,salesperson_id);
    rs = prepstmt.executeQuery();

    if(rs.next() == true){
        issalesIDhere = true;
    }else{
        issalesIDhere = false;
    }

    return issalesIDhere;

}


 public void sellPart() throws SQLException{

    Scanner scan = new Scanner(System.in);
    int part_id = 0;
    boolean exist_partid = false;
    boolean exist_salespersonid = false;
    int salesperson_id = 0;

    while(exist_partid == false){
    System.out.println("Enter The Part ID:");
    part_id = Integer.parseInt(scan.nextLine());
    exist_partid = checkpartID(part_id);
    if(exist_partid == false){
        System.out.println("[ERROR]: Unable to find Part ID.");
        }
    }

    while(exist_salespersonid == false){
        System.out.println("Enter The Salesperson ID:");
        salesperson_id = Integer.parseInt(scan.nextLine());
        exist_salespersonid = checksalesID(salesperson_id);

        if(exist_salespersonid == false){
            System.out.println("[ERROR]: Unable to find Salesperson ID.");
        }

    }

    if(exist_partid == true && exist_salespersonid == true){

        System.out.print("Product: ");

         ResultSet rs = null; 
         String statement;
         PreparedStatement prepstmt;
         
         statement = "SELECT P.pName, P.pAvailableQuantity FROM part P WHERE P.pid = ?";
         prepstmt = conn.prepareStatement(statement);
         prepstmt.setInt(1,part_id);
         rs = prepstmt.executeQuery();

        if(rs.next() == true){
         statement = "UPDATE part SET pAvailableQuantity = pAvailableQuantity - 1 WHERE pID = ?";
         prepstmt = conn.prepareStatement(statement);
         prepstmt.setInt(1, part_id);
         prepstmt.executeUpdate();
        
         statement = "SELECT P.pName, P.pAvailableQuantity FROM part P WHERE P.pID = ?";
         prepstmt = conn.prepareStatement(statement);
         prepstmt.setInt(1, part_id);
         rs = prepstmt.executeQuery();

         String partname = rs.getString(1);
         System.out.print(partname);
         System.out.print("(id: " + salesperson_id + ")");
         System.out.print("Remaining Quantity: ");
         int quantity = rs.getInt(2);
         System.out.print(quantity);

        }else{
            System.out.println("[ERROR]: Part cannot be sold.");
        }


    }



    
    }
}