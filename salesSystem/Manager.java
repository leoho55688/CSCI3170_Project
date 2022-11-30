package salesSystem;

import java.sql.*;

public class Manager {
    boolean manager_output = false;
    private Connection conn;

    public Manager (Connection c) {
        conn = c;
    }

    public void querySalespersons (int order) {}

    public void querySalespersonsByExperience (int lowerBound, int upperBound) {}

    public void querySalesValue () {}

    public void queryPopularParts (int noOfParts) {}
    
}