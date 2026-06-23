package com.Classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.Dbconn;

@WebServlet("/History")
public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        List<String[]> rows = new ArrayList<>();
        try {
            File f = new File(Dbconn.filepath + "predictions.csv");
            if (f.exists()){
                try(BufferedReader br = new BufferedReader(new FileReader(f))){
                    String line; while((line=br.readLine())!=null){
                        String[] parts = line.split(","); rows.add(parts);
                    }
                }
            }
        } catch (Exception e){ e.printStackTrace(); }
        request.setAttribute("rows", rows);
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/History.jsp");
            rd.forward(request, response);
        } catch (Exception ex){ ex.printStackTrace(); }
    }
}
