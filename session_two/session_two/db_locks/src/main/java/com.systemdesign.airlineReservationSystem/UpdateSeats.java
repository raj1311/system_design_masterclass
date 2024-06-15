package com.systemdesign.airlineReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UpdateSeats {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AirlineReservationSystem";
    private static final String USER = "root";
    private static final int NUM_USERS = 120;

    public static void main(String[] args) {
        InsertFreshBookedSeats.insertFreshBookedSeats();
        ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed

        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= NUM_USERS; i++) {
            final int userId = i;
            executor.submit(() -> updateSeat(userId));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS); // Adjust timeout as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken : " + endTime + " ms");
        DisplayBookedSeats.displayBookedSeats();
        DeleteBookedSeats.deleteBookedSeats();
    }

    private static void updateSeat(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, null);

            // Start a transaction
            conn.setAutoCommit(false);

            // Fetch seat details for the user with row-level lock
            String sqlSelect = "SELECT id, seat_no FROM FlightSeats WHERE user_id IS NULL ORDER BY id LIMIT 1 FOR UPDATE NOWAIT";
            pstmt = conn.prepareStatement(sqlSelect);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int seatId = rs.getInt("id");
                String currentSeatNo = rs.getString("seat_no");

                // Update the seat with user_id and commit the transaction
                String sqlUpdate = "UPDATE FlightSeats SET user_id = ? WHERE id = ?";
                pstmt = conn.prepareStatement(sqlUpdate);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, seatId);
                pstmt.executeUpdate();

                conn.commit();
                System.out.println("Updated seat for user ID: " + userId + " from " + currentSeatNo);
            } else {
                System.out.println("No seat found for user ID: " + userId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback in case of error
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit mode
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}