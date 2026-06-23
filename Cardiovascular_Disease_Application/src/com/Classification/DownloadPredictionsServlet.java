package com.Classification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.Dbconn;

@WebServlet("/DownloadPredictions")
public class DownloadPredictionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File f = new File(Dbconn.filepath+"predictions.csv");
        if (!f.exists()) { response.sendError(404); return; }
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=predictions.csv");
        try (FileInputStream fis = new FileInputStream(f)){
            byte[] buf = new byte[4096]; int n; while ((n=fis.read(buf))>0) response.getOutputStream().write(buf,0,n);
        }
    }
}
