package salesSystem;

import java.sql.*;

public class Manager {
    boolean manager_output = false;
    private Connection conn;

    public Manager (Connection c) {
        this.conn = c;
    }

    public void querySalespersons (int order) throws SQLException {
        String salespersons;

        try {
            if (order == 1) {
                salespersons = "select sID, sName, sPhoneNumber, sExperience from salesperson order by sExperience;";
            } else if (order == 2) {
                salespersons = "select sID, sName, sPhoneNumber, sExperience from salesperson order by sExperience desc;";
            } else {
                System.out.println("Please enter valid input.");
                return;
            }
            PreparedStatement pstmt = conn.prepareStatement(salespersons);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()){
                System.out.println("There is no salespersons in the database.");
				return;
            } else {
                System.out.println("| ID | Name | Mobile Phone | Years of Experience |");
                do {
                    int id = rs.getInt("sID");
                    String name = rs.getString("sName");
                    int phone = rs.getInt("sPhoneNumber");
                    int experience = rs.getInt("sExperience");
                    System.out.println("| " + id + " | " + name + " | " + phone + " | " + experience + " |");
                } while (rs.next());
            }
			System.out.println("End of Query");
			System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void querySalespersonsByExperience (int lowerBound, int upperBound) throws SQLException {
        String query = "select s.sID, s.sName, s.sExperience, count(*) as count " +
                       "from transaction t, salesperson s " +
                       "where s.sID = t.sID " +
                       "and s.sExperience >= " + lowerBound +
                       " and s.sExperience <= " + upperBound +
                       " group by s.sID " +
                       "order by s.sID desc";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            System.out.println("No salesperson fit your requirement.");
            return;
        } else {
            System.out.println("Transaction Record:");
            System.out.println("| ID | Name | Years of Experience | Number of Transaction |");
            do {
                int id = rs.getInt("sID");
                String name = rs.getString("sName");
                int experience = rs.getInt("sExperience");
                int count = rs.getInt("count");
                System.out.println("| " + id + " | " + name + " | " + experience + " | " + count + " |");
            } while (rs.next());
        }
        System.out.println("End of Query");
        System.out.println("");
    }

    public void querySalesValue () throws SQLException {
        String query = "select m.mID, m.mName, sum(p.pPrice) as profit " +
                       "from part p " +
                       "inner join manufacturer m " +
                       "on p.mID = m.mID " +
                       "group by m.mID " +
                       "order by profit desc";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            System.out.println("There are no sales record of any manufacturers.");
            return;
        } else {
            System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
            do {
                int id = rs.getInt("mID");
                String name = rs.getString("mName");
                int sales = rs.getInt("profit");
                System.out.println("| " + id + " | " + name + " | " + sales + " |");
            } while (rs.next());
        }
        System.out.println("End of Query");
        System.out.println("");
    }

    public void queryPopularParts (int noOfParts) throws SQLException {
        String query = "select p.pID, p.pName, count(*) as count " +
                       "from part p, transaction t " +
                       "where p.pID = t.pID " +
                       "group by p.pID " +
                       "order by count desc limit " + noOfParts;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            System.out.println("There are no data of parts.");
        } else {
            System.out.println("| Part ID | Part Name | No. of Transaction |");
            do {
                int id = rs.getInt("pID");
                String name = rs.getString("pName");
                int count = rs.getInt("count");
                System.out.println("| " + id + " | " + name + " | " + count + " |");
            } while (rs.next());
        }
        System.out.println("End of Query");
        System.out.println("");
    }

}
