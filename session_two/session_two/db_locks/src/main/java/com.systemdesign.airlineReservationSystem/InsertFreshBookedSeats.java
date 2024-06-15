package com.systemdesign.airlineReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertFreshBookedSeats {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AirlineReservationSystem";
    private static final String USER = "root";

    public static void main(String[] args) {
        insertFreshBookedSeats();
    }
    /**
     *
     *
     * INSERT INTO FlightSeats (seat_no, trip_id) VALUES  ('1-A', 1), ('1-B', 1), ('1-C', 1), ('1-D', 1), ('1-E', 1), ('1-F', 1), ('2-A', 1), ('2-B', 1), ('2-C', 1), ('2-D', 1), ('2-E', 1), ('2-F', 1), ('3-A', 1), ('3-B', 1), ('3-C', 1), ('3-D', 1), ('3-E', 1), ('3-F', 1), ('4-A', 1), ('4-B', 1), ('4-C', 1), ('4-D', 1), ('4-E', 1), ('4-F', 1), ('5-A', 1), ('5-B', 1), ('5-C', 1), ('5-D', 1), ('5-E', 1), ('5-F', 1), ('6-A', 1), ('6-B', 1), ('6-C', 1), ('6-D', 1), ('6-E', 1), ('6-F', 1), ('7-A', 1), ('7-B', 1), ('7-C', 1), ('7-D', 1), ('7-E', 1), ('7-F', 1), ('8-A', 1), ('8-B', 1), ('8-C', 1), ('8-D', 1), ('8-E', 1), ('8-F', 1), ('9-A', 1), ('9-B', 1), ('9-C', 1), ('9-D', 1), ('9-E', 1), ('9-F', 1), ('10-A', 1), ('10-B', 1), ('10-C', 1), ('10-D', 1), ('10-E', 1), ('10-F', 1),('11-A', 1), ('11-B', 1), ('11-C', 1), ('11-D', 1), ('11-E', 1), ('11-F', 1), ('12-A', 1), ('12-B', 1), ('12-C', 1), ('12-D', 1), ('12-E', 1), ('12-F', 1), ('13-A', 1), ('13-B', 1), ('13-C', 1), ('13-D', 1), ('13-E', 1), ('13-F', 1), ('14-A', 1), ('14-B', 1), ('14-C', 1), ('14-D', 1), ('14-E', 1), ('14-F', 1), ('15-A', 1), ('15-B', 1), ('15-C', 1), ('15-D', 1), ('15-E', 1), ('15-F', 1), ('16-A', 1), ('16-B', 1), ('16-C', 1), ('16-D', 1), ('16-E', 1), ('16-F', 1), ('17-A', 1), ('17-B', 1), ('17-C', 1), ('17-D', 1), ('17-E', 1), ('17-F', 1), ('18-A', 1), ('18-B', 1), ('18-C', 1), ('18-D', 1), ('18-E', 1), ('18-F', 1), ('19-A', 1), ('19-B', 1), ('19-C', 1), ('19-D', 1), ('19-E', 1), ('19-F', 1), ('20-A', 1), ('20-B', 1), ('20-C', 1), ('20-D', 1), ('20-E', 1), ('20-F', 1);
     */
    public static void insertFreshBookedSeats() {

        {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, null);

                String sqlSelect = "INSERT INTO FlightSeats (seat_no, trip_id) VALUES  ('1-A', 1), ('1-B', 1), ('1-C', 1), ('1-D', 1), ('1-E', 1), ('1-F', 1), ('2-A', 1), ('2-B', 1), ('2-C', 1), ('2-D', 1), ('2-E', 1), ('2-F', 1), ('3-A', 1), ('3-B', 1), ('3-C', 1), ('3-D', 1), ('3-E', 1), ('3-F', 1), ('4-A', 1), ('4-B', 1), ('4-C', 1), ('4-D', 1), ('4-E', 1), ('4-F', 1), ('5-A', 1), ('5-B', 1), ('5-C', 1), ('5-D', 1), ('5-E', 1), ('5-F', 1), ('6-A', 1), ('6-B', 1), ('6-C', 1), ('6-D', 1), ('6-E', 1), ('6-F', 1), ('7-A', 1), ('7-B', 1), ('7-C', 1), ('7-D', 1), ('7-E', 1), ('7-F', 1), ('8-A', 1), ('8-B', 1), ('8-C', 1), ('8-D', 1), ('8-E', 1), ('8-F', 1), ('9-A', 1), ('9-B', 1), ('9-C', 1), ('9-D', 1), ('9-E', 1), ('9-F', 1), ('10-A', 1), ('10-B', 1), ('10-C', 1), ('10-D', 1), ('10-E', 1), ('10-F', 1),('11-A', 1), ('11-B', 1), ('11-C', 1), ('11-D', 1), ('11-E', 1), ('11-F', 1), ('12-A', 1), ('12-B', 1), ('12-C', 1), ('12-D', 1), ('12-E', 1), ('12-F', 1), ('13-A', 1), ('13-B', 1), ('13-C', 1), ('13-D', 1), ('13-E', 1), ('13-F', 1), ('14-A', 1), ('14-B', 1), ('14-C', 1), ('14-D', 1), ('14-E', 1), ('14-F', 1), ('15-A', 1), ('15-B', 1), ('15-C', 1), ('15-D', 1), ('15-E', 1), ('15-F', 1), ('16-A', 1), ('16-B', 1), ('16-C', 1), ('16-D', 1), ('16-E', 1), ('16-F', 1), ('17-A', 1), ('17-B', 1), ('17-C', 1), ('17-D', 1), ('17-E', 1), ('17-F', 1), ('18-A', 1), ('18-B', 1), ('18-C', 1), ('18-D', 1), ('18-E', 1), ('18-F', 1), ('19-A', 1), ('19-B', 1), ('19-C', 1), ('19-D', 1), ('19-E', 1), ('19-F', 1), ('20-A', 1), ('20-B', 1), ('20-C', 1), ('20-D', 1), ('20-E', 1), ('20-F', 1)";
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
}
