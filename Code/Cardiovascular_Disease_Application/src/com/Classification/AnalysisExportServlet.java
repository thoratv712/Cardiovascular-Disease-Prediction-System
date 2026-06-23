package com.Classification;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.Dbconn;

@WebServlet("/AnalysisExport")
public class AnalysisExportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition","attachment; filename=analysis_metrics.csv");
        try(PrintWriter pw = response.getWriter()){
            // ensure values loaded
            Dbconn.configuration_matrix_values_RF();
            Dbconn.configuration_matrix_values_SVM();
            Dbconn.configuration_matrix_values_HML();

            pw.println("Algorithm,Accuracy,Precision,Recall,F1");
            pw.printf("RF,%s,%s,%s,%s\n", Dbconn.rf_acc, Dbconn.rf_pre, Dbconn.rf_recall, Dbconn.rf_f1_score);
            pw.printf("SVM,%s,%s,%s,%s\n", Dbconn.SVM_acc, Dbconn.SVM_pre, Dbconn.SVM_recall, Dbconn.SVM_f1_score);
            pw.printf("HML,%s,%s,%s,%s\n", Dbconn.hml_acc, Dbconn.hml_pre, Dbconn.hml_recall, Dbconn.hml_f1_score);
        }
    }
}
