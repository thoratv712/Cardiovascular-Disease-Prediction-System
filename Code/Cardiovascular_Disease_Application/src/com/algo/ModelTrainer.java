package com.algo;

import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;

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

        AdvancedEnsemble.runStacking(train,test);
        
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
}