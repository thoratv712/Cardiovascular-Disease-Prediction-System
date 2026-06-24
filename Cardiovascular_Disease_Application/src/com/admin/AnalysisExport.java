package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.connection.Dbconn;

public class AnalysisExport extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AnalysisExport() {
        super();
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null ||
            session.getAttribute("email") == null) {
            response.sendRedirect("loginpage");
            return;
        }

        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition",
                "attachment; filename=\"Performance_Metrics.csv\"");

            PrintWriter pw = response.getWriter();
            pw.println("Algorithm,Accuracy,Precision,Recall,F1_Score");

            try {
                Connection conn = Dbconn.conn();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT Algorithm_Name, Accuracy_values, " +
                    "precision_values, recall_values, fMeasure_values " +
                    "FROM tblanalysis ORDER BY Algorithm_Name");

                while (rs.next()) {
                    pw.println(
                        rs.getString(1) + "," +
                        rs.getString(2) + "," +
                        rs.getString(3) + "," +
                        rs.getString(4) + "," +
                        rs.getString(5)
                    );
                }
                rs.close();
                st.close();
                conn.close();

            } catch (Exception dbEx) {
                System.out.println("DB failed, using static values: "
                    + dbEx.getMessage());

                pw.println("RF,"
                    + Dbconn.rf_acc + ","
                    + Dbconn.rf_pre + ","
                    + Dbconn.rf_recall + ","
                    + Dbconn.rf_f1_score);

                pw.println("SVM,"
                    + Dbconn.SVM_acc + ","
                    + Dbconn.SVM_pre + ","
                    + Dbconn.SVM_recall + ","
                    + Dbconn.SVM_f1_score);

                pw.println("HML,"
                    + Dbconn.hml_acc + ","
                    + Dbconn.hml_pre + ","
                    + Dbconn.hml_recall + ","
                    + Dbconn.hml_f1_score);
            }

            pw.flush();
            pw.close();
            System.out.println("Metrics exported successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}