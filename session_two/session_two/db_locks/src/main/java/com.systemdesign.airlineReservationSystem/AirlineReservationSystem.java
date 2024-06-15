package com.systemdesign.airlineReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class AirlineReservationSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AirlineReservationSystem";
    private static final String USER = "root";
//    private static final String PASS = "yourPassword";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Step 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,null);

            // Step 3: Execute a query to select all users
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_id, username FROM Users";
            ResultSet rs = stmt.executeQuery(sql);

            // Step 4: Extract data from result set
            System.out.println("User List:");
            while (rs.next()) {
                // Retrieve by column name
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");

                // Display values
                System.out.print("ID: " + userId);
                System.out.println(", Username: " + username);
            }

            // Clean-up environment
            rs.close();

            // Step 5: Execute a query to select all trips
            sql = "SELECT trip_id, flight_name FROM Trips";
            rs = stmt.executeQuery(sql);

            // Extract data from result set
            System.out.println("Trip List:");
            while (rs.next()) {
                // Retrieve by column name
                int tripId = rs.getInt("trip_id");
                String flightName = rs.getString("flight_name");

                // Display values
                System.out.print("Trip ID: " + tripId);
                System.out.println(", Flight Name: " + flightName);
            }

            // Clean-up environment
            rs.close();

            // Step 6: Execute a query to select all flight seats with user reservations
            sql = "SELECT fs.id, fs.seat_no, fs.trip_id, fs.user_id, u.username " +
                  "FROM FlightSeats fs " +
                  "LEFT JOIN Users u ON fs.user_id = u.user_id";
            rs = stmt.executeQuery(sql);

            // Extract data from result set
            System.out.println("Flight Seats:");
            while (rs.next()) {
                // Retrieve by column name
                int seatId = rs.getInt("id");
                String seatNo = rs.getString("seat_no");
                int tripId = rs.getInt("trip_id");
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");

                // Display values
                System.out.print("Seat ID: " + seatId);
                System.out.print(", Seat No: " + seatNo);
                System.out.print(", Trip ID: " + tripId);
                System.out.print(", User ID: " + userId);
                System.out.println(", Username: " + username);
            }

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");
    }
}