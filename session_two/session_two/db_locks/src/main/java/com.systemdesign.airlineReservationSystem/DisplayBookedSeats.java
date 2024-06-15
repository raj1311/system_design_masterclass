package com.systemdesign.airlineReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayBookedSeats {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AirlineReservationSystem";
    private static final String USER = "root";
    private static final int NUM_ROWS = 6; // Number of rows (1 to 6)
    private static final int NUM_COLS = 20; // Number of columns (A to F)

    public static void main(String[] args) {
        displayBookedSeats();
    }

    public static void displayBookedSeats() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, null);

            String sqlSelect = "SELECT seat_no,user_id FROM FlightSeats WHERE trip_id = 1 ORDER BY id";
            pstmt = conn.prepareStatement(sqlSelect);
            rs = pstmt.executeQuery();

            // Initialize a 2D array to hold seat statuses
            char[][] seatGrid = new char[NUM_ROWS][NUM_COLS];

            // Populate seatGrid with 'X' (empty seats)
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    seatGrid[i][j] = 'X';
                }
            }

            // Fill seatGrid with booked seats marked as 'O'
            while (rs.next()) {
                String seatNo = rs.getString("seat_no");
                Integer userId = rs.getInt("user_id");
                // Parse seat number (assuming format "row-col", e.g., "1-A")
                int col = Integer.parseInt(seatNo.split("-")[0]) - 1; // Adjust to 0-indexed
                char row = (char) (seatNo.split("-")[1].charAt(0) - 'A'); // Adjust to 0-indexed

                if(userId != 0) {
                    seatGrid[row][col] = 'O';
                }// Mark seat as booked with 'O'
            }

            // Print seat map
            for (int i = 0; i < NUM_ROWS; i++) {
                    for (int j = 0; j < NUM_COLS; j++) {
                        System.out.print(seatGrid[i][j] + " ");

                }
                System.out.println();
                    if(i==NUM_ROWS/2-1) {
                        System.out.println();
                    }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}