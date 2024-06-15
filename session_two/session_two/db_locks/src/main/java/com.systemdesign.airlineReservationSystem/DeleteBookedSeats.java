package com.systemdesign.airlineReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteBookedSeats {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AirlineReservationSystem";
    private static final String USER = "root";

    public static void main(String[] args) {
        deleteBookedSeats();
    }
    public static void deleteBookedSeats() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, null);

            String sqlSelect = "DELETE from FlightSeats";
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.execute();

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
