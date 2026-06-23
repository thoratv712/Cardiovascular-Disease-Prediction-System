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

import com.algo.DataLoader;
import com.connection.Dbconn;

import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

@WebServlet("/EDA")
public class EDAServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Instances data = DataLoader.loadCSV(Dbconn.DB_model);

            // Remove id/patientid if present as first attribute
            Attribute a0 = data.attribute(0);
            if (a0 != null && (a0.name().toLowerCase().contains("id") || a0.name().toLowerCase().contains("patient"))) {
                Remove rem = new Remove();
                rem.setAttributeIndices("1");
                rem.setInputFormat(data);
                data = Filter.useFilter(data, rem);
            }

            int rows = data.numInstances();
            int cols = data.numAttributes();

            // Missing counts
            Map<String,Integer> missing = new HashMap<>();
            for (int i=0;i<data.numAttributes();i++){
                int miss=0;
                for (int j=0;j<data.numInstances();j++) if (data.instance(j).isMissing(i)) miss++;
                missing.put(data.attribute(i).name(), miss);
            }

            // class distribution
            Map<String,Integer> classDist = new HashMap<>();
            int classIndex = data.classIndex();
            if (classIndex<0) classIndex = data.numAttributes()-1;
            for (int i=0;i<data.numInstances();i++){
                String v = data.instance(i).stringValue(classIndex);
                classDist.put(v, classDist.getOrDefault(v,0)+1);
            }

            // Features list (excluding class)
            List<String> features = new ArrayList<>();
            for (int i=0;i<data.numAttributes();i++) if (i!=classIndex) features.add(data.attribute(i).name());

            // Correlation matrix for numeric attributes (excluding class)
            int n = features.size();
            double[][] corr = new double[n][n];
            // prepare numeric arrays
            double[][] values = new double[n][rows];
            for (int fi=0,ai=0;fi<data.numAttributes();fi++){
                if (fi==classIndex) continue;
                for (int r=0;r<rows;r++){
                    double val = Double.NaN;
                    if (!data.instance(r).isMissing(fi)) val = data.instance(r).value(fi);
                    values[ai][r] = val;
                }
                ai++;
            }
            // compute correlation
            for (int i=0;i<n;i++){
                for (int j=0;j<n;j++){
                    double meanI=0,meanJ=0; int cntI=0,cntJ=0;
                    for (int r=0;r<rows;r++){ if (!Double.isNaN(values[i][r])){ meanI+=values[i][r]; cntI++; } if (!Double.isNaN(values[j][r])){ meanJ+=values[j][r]; cntJ++; } }
                    if (cntI>0) meanI/=cntI; if (cntJ>0) meanJ/=cntJ;
                    double num=0, denI=0, denJ=0; int cnt=0;
                    for (int r=0;r<rows;r++){
                        if (Double.isNaN(values[i][r])||Double.isNaN(values[j][r])) continue;
                        num += (values[i][r]-meanI)*(values[j][r]-meanJ);
                        denI += Math.pow(values[i][r]-meanI,2);
                        denJ += Math.pow(values[j][r]-meanJ,2);
                        cnt++;
                    }
                    double denom = Math.sqrt(denI*denJ);
                    corr[i][j] = (denom==0)?0:(num/denom);
                }
            }

            // Histograms: 10 bins for each feature
            Map<String,int[]> hist = new HashMap<>();
            Map<String,double[]> binEdges = new HashMap<>();
            for (int i=0,ai=0;i<data.numAttributes();i++){
                if (i==classIndex) continue;
                double min=Double.POSITIVE_INFINITY, max=Double.NEGATIVE_INFINITY;
                for (int r=0;r<rows;r++){
                    if (data.instance(r).isMissing(i)) continue;
                    double v = data.instance(r).value(i);
                    if (v<min) min=v; if (v>max) max=v;
                }
                int bins=10;
                int[] counts = new int[bins];
                double[] edges = new double[bins+1];
                if (min==Double.POSITIVE_INFINITY || min==max){
                    // all missing or constant
                    for (int b=0;b<bins;b++) counts[b]=0;
                    for (int b=0;b<=bins;b++) edges[b]=min+b;
                } else {
                    double width = (max-min)/bins;
                    for (int b=0;b<=bins;b++) edges[b]=min + b*width;
                    for (int r=0;r<rows;r++){
                        if (data.instance(r).isMissing(i)) continue;
                        double v = data.instance(r).value(i);
                        int bin = (int) Math.floor((v-min)/width);
                        if (bin<0) bin=0; if (bin>=bins) bin=bins-1;
                        counts[bin]++;
                    }
                }
                hist.put(data.attribute(i).name(), counts);
                binEdges.put(data.attribute(i).name(), edges);
                ai++;
            }

            request.setAttribute("rows", rows);
            request.setAttribute("cols", cols);
            request.setAttribute("missing", missing);
            request.setAttribute("classDist", classDist);
            request.setAttribute("features", features);
            request.setAttribute("corr", corr);
            request.setAttribute("hist", hist);
            request.setAttribute("binEdges", binEdges);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("rows", 0);
            request.setAttribute("cols", 0);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/EDA.jsp");
        try { rd.forward(request, response); } catch (Exception ex){ ex.printStackTrace(); }
    }
}
