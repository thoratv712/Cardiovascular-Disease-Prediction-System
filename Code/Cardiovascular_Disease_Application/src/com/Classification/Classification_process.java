package com.Classification;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weka.core.Instances;
import com.algo.ModelTrainer;
import com.connection.Dbconn;

/**
 * Servlet implementation class Classification_process
 */
@WebServlet("/Classification_process")
public class Classification_process extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Classification_process() {
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
			// Train/Test Split (70/30)
			Instances data = Dbconn.loadARFF(Dbconn.PSO_Selected_Features_model);

			data.randomize(new Random(1));

			int trainSize = (int) (data.numInstances() * 0.7);
			int testSize = data.numInstances() - trainSize;

			Instances train = new Instances(data, 0, trainSize);
			Instances test = new Instances(data, trainSize, testSize);
			// Train & Evaluate
			ModelTrainer.train(train, test);

			pw.println("<script> alert('Classification Successfully');</script>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

		RequestDispatcher rd = request
				.getRequestDispatcher("/AnalysisPage.jsp");

		rd.include(request, response);
	}

}
