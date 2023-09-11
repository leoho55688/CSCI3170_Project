package salesSystem;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Salesperson {
    private Connection conn;

    public Salesperson (Connection c) {
        conn = c;
    }

    public void queryParts (int criteria, String keyword, int order) throws SQLException {
        String ordering = "";
        if (order == 1) {
            ordering = "asc";
        } else {
            ordering = "desc";
        }

        String criterion = "";
        if (criteria == 1) {
            criterion = "p.pName";
        } else {
            criterion = "m.mName";
        }

        String query = "select p.pID, p.pName, m.mName, c.cName, p.pAvailableQuantity, p.pWarrantyPeriod, p.pPrice " +
                "from part p, manufacturer m, category c " +
                "where p.cID = c.cID " +
                "and m.mID = p.mID " +
                "and " + criterion + " like '%" + keyword + "%'" +
                "order by p.pPrice " + ordering;

        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) {
            System.out.println("No parts match your criterion.");
        } else {
            System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
            do {
                int id = rs.getInt("pID");
                String pName = rs.getString("pName");
                String mName = rs.getString("mName");
                String cName = rs.getString("cName");
                int quantity = rs.getInt("pAvailableQuantity");
                int warranty = rs.getInt("pWarrantyPeriod");
                int price = rs.getInt("pPrice");
                System.out.println("| " + id + " | " + pName + " | " + mName + " | " + cName + " | " + quantity + " | " + warranty + " | " + price + " |");
            } while (rs.next());
        }
        System.out.println("End of Query");
		System.out.println("");
    }

    public void sellPart (int pID, int sID) throws SQLException {
        int quantity = 0;
        int tID = 0;
        String pName;
        PreparedStatement pstmt;
        ResultSet rs;

        String checkSalesperson = "select sID from salesperson where sID = " + sID;
        pstmt = conn.prepareStatement(checkSalesperson);
        rs = pstmt.executeQuery();
        if (!rs.next()) {
            System.out.println("[ERROR] the salesperson does not exist.");
            return;
        }
        String checkPart = "select pName, pAvailableQuantity from part where pID = " + pID;
        pstmt = conn.prepareStatement(checkPart);
        rs = pstmt.executeQuery();
        if (!rs.next()) {
           System.out.println("[ERROR] the part does not exist.");
           return;
        }
        quantity = rs.getInt("pAvailableQuantity");
        pName = rs.getString("pName");

        String update = "update part set pAvailableQuantity = pAvailableQuantity - 1 where pID = " + pID;
        pstmt = conn.prepareStatement(update);
        pstmt.executeUpdate();

        String trans = "select * from transaction order by tID desc limit 1";
        pstmt = conn.prepareStatement(trans);
        rs = pstmt.executeQuery();
        rs.next();
        tID = rs.getInt("tID");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String insertTransaction = "insert into transaction values (?, ?, ?, ?)";
        pstmt = conn.prepareStatement(insertTransaction);
        pstmt.setInt(1, tID + 1);
        pstmt.setInt(2, pID);
        pstmt.setInt(3, sID);
        pstmt.setString(4, dtf.format(now));
        pstmt.executeUpdate();
        System.out.println("Product: " + pName + "(id: " + pID + ") Remaining Quantity: " + (quantity - 1));
    }
}