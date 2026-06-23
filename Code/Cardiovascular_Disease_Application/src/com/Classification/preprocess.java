package com.Classification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import weka.core.Instances;

import com.admin.DatabaseToCSV;
import com.algo.DataLoader;
import com.algo.Preprocessing;
import com.connection.Dbconn;

/**
 * Servlet implementation class preprocess
 */
@WebServlet("/preprocess")
public class preprocess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public preprocess() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		try {
			DatabaseToCSV.csvwriting();
			// 1. Load CSV
			Instances data1 = DataLoader
					.loadCSV(Dbconn.DB_model);
			// Convert class to nominal FIRST
			Preprocessing.Preprocessing_model(data1);

			pw.println("<script> alert('Upload Dataset Patient Successfully');</script>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

		RequestDispatcher rd = request
				.getRequestDispatcher("/PatientHomePage.jsp");

		rd.include(request, response);
	}

}
