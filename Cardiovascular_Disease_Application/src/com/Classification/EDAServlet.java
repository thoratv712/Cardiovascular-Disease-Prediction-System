package com.Classification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weka.core.Instances;

import com.algo.DataLoader;
import com.connection.Dbconn;

@WebServlet("/EDA")
public class EDAServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Instances data = DataLoader.loadCSV(Dbconn.DB_model);
            int rows = data.numInstances();
            int cols = data.numAttributes();

            Map<String,Integer> missing = new HashMap<>();
            List<String> features = new ArrayList<>();
            for (int i=0;i<data.numAttributes();i++){
                features.add(data.attribute(i).name());
                int miss=0; for (int j=0;j<data.numInstances();j++) if (data.instance(j).isMissing(i)) miss++;
                missing.put(data.attribute(i).name(), miss);
            }

            double[][] corr = new double[cols][cols];
            for (int i=0;i<cols;i++) for (int j=0;j<cols;j++) corr[i][j]=0.0;

            Map<String,int[]> hist = new HashMap<>();
            Map<String,double[]> binEdges = new HashMap<>();
            for (int i=0;i<cols;i++){
                double min=Double.POSITIVE_INFINITY, max=Double.NEGATIVE_INFINITY;
                for (int r=0;r<rows;r++){ double v=data.instance(r).value(i); if (!Double.isNaN(v)){ if (v<min) min=v; if (v>max) max=v; }}
                int bins=10; int[] counts=new int[bins]; double[] edges=new double[bins+1];
                for (int b=0;b<=bins;b++) edges[b]=min + (max-min)*b/bins;
                for (int r=0;r<rows;r++){ double v=data.instance(r).value(i); if (!Double.isNaN(v)){ int bin=(int)((v-min)/(max-min+1e-9)*bins); if (bin<0) bin=0; if (bin>=bins) bin=bins-1; counts[bin]++; }}
                hist.put(data.attribute(i).name(), counts);
                binEdges.put(data.attribute(i).name(), edges);
            }

            request.setAttribute("rows", rows);
            request.setAttribute("cols", cols);
            request.setAttribute("missing", missing);
            request.setAttribute("features", features);
            request.setAttribute("corr", corr);
            request.setAttribute("hist", hist);
            request.setAttribute("binEdges", binEdges);
        } catch (Exception e){ e.printStackTrace(); }

        RequestDispatcher rd = request.getRequestDispatcher("/EDA.jsp");
        rd.forward(request, response);
    }
}
