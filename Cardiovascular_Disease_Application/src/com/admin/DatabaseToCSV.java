package com.admin;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import com.connection.Dbconn;

public class DatabaseToCSV {

    public static void csvwriting() {

        String query = "SELECT * FROM heart_data";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        FileWriter writer = null;

        try {
            conn = Dbconn.conn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            writer = new FileWriter(Dbconn.DB_model);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Write Header
            for (int i = 1; i <= columnCount; i++) {
                writer.append(metaData.getColumnName(i));
                if (i != columnCount) writer.append(",");
            }
            writer.append("\n");

            // Write Data Rows (FIXED)
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {

                    String value = rs.getString(i);

                    // 🔥 IMPORTANT FIX: handle null & empty
                    if (value == null || value.trim().isEmpty()) {
                        writer.append("0");  // replace missing values
                    } else {
                        writer.append(value.trim());
                    }

                    if (i != columnCount) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            System.out.println("Data exported to CSV successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        csvwriting();  // ✅ Call method for testing
    }
}