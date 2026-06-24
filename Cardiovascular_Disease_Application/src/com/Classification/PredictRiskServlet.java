package com.Classification;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;

import com.algo.DataLoader;
import com.connection.Dbconn;

public class PredictRiskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {
        RequestDispatcher rd =
            request.getRequestDispatcher("/PredictRisk.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            String ageStr    = request.getParameter("age");
            String genderStr = request.getParameter("gender");

            double age       = Double.parseDouble(ageStr);
            double gender    = Double.parseDouble(genderStr);
            double chestpain = Double.parseDouble(
                request.getParameter("chestpain"));
            double restingBP = Double.parseDouble(
                request.getParameter("restingBP"));
            double serum     = Double.parseDouble(
                request.getParameter("serumcholestrol"));
            double fasting   = Double.parseDouble(
                request.getParameter("fastingbloodsugar"));

            // Load dataset
            Instances data = DataLoader.loadCSV(Dbconn.DB_model);

            // Remove ID column
            Remove rem = new Remove();
            rem.setAttributeIndices("1");
            rem.setInputFormat(data);
            Instances filtered = Filter.useFilter(data, rem);

            // Convert class to Nominal - fixes InfoGain error
            NumericToNominal convert = new NumericToNominal();
            convert.setOptions(new String[]{"-R", "last"});
            convert.setInputFormat(filtered);
            Instances nominalData = Filter.useFilter(
                filtered, convert);
            nominalData.setClassIndex(
                nominalData.numAttributes() - 1);

            // Train Random Forest
            RandomForest rf = new RandomForest();
            rf.setNumIterations(100);
            rf.buildClassifier(nominalData);

            // Build prediction instance
            Instances header = new Instances(nominalData, 0);
            double[] vals = new double[header.numAttributes()];

            vals[0] = age;
            vals[1] = gender;
            vals[2] = chestpain;
            vals[3] = restingBP;
            vals[4] = serum;
            vals[5] = fasting;

            for (int i = 6; i < vals.length - 1; i++) {
                vals[i] = Utils.missingValue();
            }
            vals[vals.length - 1] = Utils.missingValue();

            Instance inst = new DenseInstance(1.0, vals);
            inst.setDataset(header);

            // Predict
            double pred  = rf.classifyInstance(inst);
            double[] dist = rf.distributionForInstance(inst);

            String predClass = header.classAttribute()
                .value((int) pred);
            double prob = dist.length > 1 ? dist[1] : dist[0];

            String resultText = predClass.equals("1")
                ? "High Risk of CVD" : "Low Risk";

            // Feature importance
            InfoGainAttributeEval eval =
                new InfoGainAttributeEval();
            eval.buildEvaluator(nominalData);

            Map<String, Double> scores =
                new LinkedHashMap<>();
            for (int i = 0;
                 i < nominalData.numAttributes() - 1; i++) {
                scores.put(
                    nominalData.attribute(i).name(),
                    eval.evaluateAttribute(i));
            }

            List<Map.Entry<String, Double>> entries =
                new ArrayList<>(scores.entrySet());
            entries.sort((a, b) ->
                Double.compare(b.getValue(), a.getValue()));

            List<String> top = new ArrayList<>();
            for (int i = 0;
                 i < Math.min(5, entries.size()); i++) {
                top.add(entries.get(i).getKey()
                    + " (score="
                    + String.format("%.4f",
                        entries.get(i).getValue())
                    + ")");
            }

            // Generate Patient ID
            String patientId = "P"
                + (System.currentTimeMillis() % 10000);
            String dateStr = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm").format(new Date());

            // Save to session history
            HttpSession session = request.getSession();
            List<String[]> history = (List<String[]>)
                session.getAttribute("predictionHistory");
            if (history == null)
                history = new ArrayList<>();

            history.add(0, new String[]{
                patientId,
                ageStr,
                genderStr,
                resultText,
                String.format("%.1f", prob * 100),
                dateStr
            });

            if (history.size() > 10)
                history = history.subList(0, 10);

            session.setAttribute(
                "predictionHistory", history);

            // Save to CSV log
            try (FileWriter fw = new FileWriter(
                    new java.io.File(
                        Dbconn.filepath
                        + "predictions.csv"), true)) {
                fw.append(patientId).append(",")
                  .append(dateStr).append(",")
                  .append(String.valueOf(age)).append(",")
                  .append(String.valueOf(gender)).append(",")
                  .append(resultText).append(",")
                  .append(String.format("%.4f", prob))
                  .append('\n');
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Set response attributes
            request.setAttribute("prediction", resultText);
            request.setAttribute("probability",
                String.format("%.4f", prob));
            request.setAttribute("topFeatures", top);
            request.setAttribute("patientId", patientId);
            request.setAttribute("modelUsed",
                "Random Forest");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("prediction",
                "Error: " + e.getMessage());
        }

        RequestDispatcher rd =
            request.getRequestDispatcher("/PredictRisk.jsp");
        rd.forward(request, response);
    }
}