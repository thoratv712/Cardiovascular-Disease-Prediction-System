package com.algo;


import ml.dmlc.xgboost4j.java.*;

import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.connection.Dbconn;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class ModelTrainer {

    public static void train(Instances train, Instances test) throws Exception {

        // Random Forest
        RandomForest rf = new RandomForest();
        rf.setNumIterations(150);       // increase trees
        rf.setMaxDepth(10);        // control overfitting
        rf.setNumFeatures(8);      // try sqrt(features)
        rf.buildClassifier(train);
        evaluate("RF", rf, train, test,1);

        // SVM
        SMO svm = new SMO();
        PolyKernel kernel = new PolyKernel();
        kernel.setExponent(2);   // try 1,2,3

        svm.setKernel(kernel);
        svm.setC(2.0);           // try 1–5

        svm.buildClassifier(train);
        evaluate("SVM", svm, train, test,2);

        runXGBoost(train, test, 3);
      //  AdvancedEnsemble.runStacking(train,test);
        
    }

    public static void evaluate(String name, weka.classifiers.Classifier cls,
                                Instances train, Instances test,int id) throws Exception {
    	DecimalFormat df = new DecimalFormat("#.##");
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(cls, test);

        String acc=df.format(eval.pctCorrect());
        String pre=df.format(eval.precision(1)*100);
        String recall=df.format(eval.recall(1)*100);
        String f1score=df.format(eval.fMeasure(1)*100);
        System.out.println("\n==== " + name + " ====");
        System.out.println("Accuracy: " + acc);
        System.out.println("Precision: " + pre);
        System.out.println("Recall: " + recall);
        System.out.println("F1 Score: " + f1score);
        Connection con=Dbconn.conn();
        Statement st1 = (Statement) con.createStatement();
		st1.executeUpdate("update tblanalysis set Accuracy_values='" + acc
				+ "',precision_values='" + pre + "',recall_values='"
				+ recall + "',fMeasure_values='" + f1score
				+ "' where id='"+id+"' and Algorithm_Name='"+name+"'");
    }
    public static void runXGBoost(Instances train, Instances test, int id) throws Exception {

        String trainFile = "train.csv";
        String testFile = "test.csv";

        // Convert Instances → CSV (label first)
        saveAsCSV(train, trainFile);
        saveAsCSV(test, testFile);

        // Load into XGBoost
        DMatrix trainMat = new DMatrix(trainFile + "?format=csv&label_column=0");
        DMatrix testMat = new DMatrix(testFile + "?format=csv&label_column=0");

        // Parameters
        Map<String, Object> params = new HashMap<>();
        params.put("objective", "binary:logistic");
        params.put("max_depth", 6);
        params.put("eta", 0.1);
        params.put("subsample", 0.8);
        params.put("colsample_bytree", 0.8);
        params.put("eval_metric", "logloss");

        int rounds = 50;

        // Train
        Booster model = XGBoost.train(trainMat, params, rounds, new HashMap<>(), null, null);

     // Predict
        float[][] preds = model.predict(testMat);
        float[] labels = testMat.getLabel();

        int TP = 0, TN = 0, FP = 0, FN = 0;

        for (int i = 0; i < preds.length; i++) {

            int pred = preds[i][0] > 0.5 ? 1 : 0;
            int actual = (int) labels[i];

            if (pred == 1 && actual == 1) TP++;
            else if (pred == 0 && actual == 0) TN++;
            else if (pred == 1 && actual == 0) FP++;
            else if (pred == 0 && actual == 1) FN++;
        }

        // Metrics calculation
        double accuracy = (double) (TP + TN) / (TP + TN + FP + FN) * 100;

        double precision = (TP + FP) == 0 ? 0 : (double) TP / (TP + FP) * 100;
        double recall = (TP + FN) == 0 ? 0 : (double) TP / (TP + FN) * 100;
        double f1 = (precision + recall) == 0 ? 0 : 
                    2 * (precision * recall) / (precision + recall);

        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("\n==== XGBoost ====");
        System.out.println("Accuracy: " + df.format(accuracy));
        System.out.println("Precision: " + df.format(precision));
        System.out.println("Recall: " + df.format(recall));
        System.out.println("F1 Score: " + df.format(f1));
        
        // Store in DB
        Connection con = Dbconn.conn();
        Statement st = con.createStatement();

        st.executeUpdate("update tblanalysis set Accuracy_values='" + df.format(accuracy)
                + "',precision_values='" + df.format(accuracy)
                + "',recall_values='" + df.format(accuracy)
                + "',fMeasure_values='" + df.format(accuracy)
                + "' where id='3' and Algorithm_Name='HML'");
    }
    public static void saveAsCSV(Instances data, String filePath) throws Exception {

        java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(filePath));

        int classIndex = data.classIndex();

        for (int i = 0; i < data.numInstances(); i++) {

            StringBuilder sb = new StringBuilder();

            // First column = label
            sb.append(data.instance(i).value(classIndex));

            //  features
            for (int j = 0; j < data.numAttributes(); j++) {
                if (j != classIndex) {
                    sb.append(",").append(data.instance(i).value(j));
                }
            }

            writer.write(sb.toString());
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }
}