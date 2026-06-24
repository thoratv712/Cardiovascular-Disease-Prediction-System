package com.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.connection.Dbconn;

/**
 * Servlet implementation class Datasetupload
 */
//@WebServlet("/Datasetupload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 50, // 50MB
maxFileSize = 1024 * 2048 * 100, // 100 MB

maxRequestSize = 1024 * 2048 * 1000)
public class Datasetupload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Datasetupload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		Part filePart = request.getPart("txt_search");
		InputStream inputStream = filePart.getInputStream();

		BufferedReader br = null;
		int i = 0;
		String line;
		try {
			Connection conn;
			conn = Dbconn.conn();
			Statement sts = conn.createStatement();
			sts.executeUpdate("truncate TABLE heart_data");
			br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {

				String[] d = line.split(",");

				if (i == 0) {
				} else {
					sts.executeUpdate("INSERT INTO heart_data(age, gender, chestpain, restingBP, "
							+ "serumcholestrol, fastingbloodsugar, restingrelectro, maxheartrate, "
							+ "exerciseangia, oldpeak, slope,"
							+ " noofmajorvessels, target)VALUES('"
							+ d[1]
							+ "','"
							+ d[2]
							+ "','"
							+ d[3]
							+ "','"
							+ d[4]
							+ "','"
							+ d[5]
							+ "','"
							+ d[6]
							+ "','"
							+ d[7]
							+ "','"
							+ d[8]
							+ "','"
							+ d[9]
							+ "','"
							+ d[10]
							+ "','"
							+ d[11]
							+ "','"
							+ d[12]
							+ "','"
							+ d[13]
							+ "')");
					System.out.println(line);
				}
				i++;
			}

			pw.println("<script> alert('Upload Dataset Patient Successfully');</script>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		RequestDispatcher rd = request
				.getRequestDispatcher("/PatientHomePage.jsp");

		rd.include(request, response);
	}

}
