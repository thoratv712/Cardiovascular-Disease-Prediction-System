package com.Classification;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weka.core.Instances;

import com.admin.DatabaseToCSV;
import com.algo.DataLoader;
import com.algo.FeatureSelectionH;
import com.algo.PSOFeatureSelection;
import com.algo.Preprocessing;
import com.connection.Dbconn;

/**
 * Servlet implementation class FeatureSelection
 */
@WebServlet("/FeatureSelection")
public class FeatureSelection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeatureSelection() {
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
			// FeatureSelection
			Instances data2 = Dbconn.loadARFF(Dbconn.Preprocessing_model);
			// Debug check
			System.out.println("Class Attribute: " + data2.classAttribute());

			FeatureSelectionH.fcbfRelief(data2);

			// PSO Optimization
			Instances data3 = Dbconn.loadARFF(Dbconn.FeatureSelectionH_model);

			PSOFeatureSelection.optimize(data3);

			pw.println("<script> alert('Feature Selection Successfully');</script>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

		RequestDispatcher rd = request
				.getRequestDispatcher("/PatientHomePage.jsp");

		rd.include(request, response);
	}

}
