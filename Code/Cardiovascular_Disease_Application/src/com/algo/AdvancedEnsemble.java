package com.algo;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;

import com.connection.Dbconn;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Stacking;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class AdvancedEnsemble {

    public static void runStacking(Instances train, Instances test) throws Exception {

    	DecimalFormat df = new DecimalFormat("#.##");
        // ANN (MLP)
        MultilayerPerceptron ann = new MultilayerPerceptron();
        ann.setLearningRate(0.3);
        ann.setMomentum(0.2);
        ann.setTrainingTime(300);
        ann.setHiddenLayers("a"); // auto

        // J48
        J48 j48 = new J48();
        j48.setConfidenceFactor(0.25f);
        j48.setMinNumObj(2);

        // AdaBoost
        AdaBoostM1 ada = new AdaBoostM1();
        ada.setClassifier(new J48());
        ada.setNumIterations(50);

        // Naive Bayes
        NaiveBayes nb = new NaiveBayes();

        // Meta Classifier (BEST)
        Logistic meta = new Logistic();

        // Stacking
        Stacking stack = new Stacking();
        Classifier[] baseModels = {ann, j48, ada, nb};

        stack.setClassifiers(baseModels);
        stack.setMetaClassifier(meta);

        stack.buildClassifier(train);

      //  SerializationHelper.write(heart_model.model", stack);
        //Evaluation
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(stack, test);
        String acc=df.format(eval.pctCorrect());
        String pre=df.format(eval.precision(1)*100);
        String recall=df.format(eval.recall(1)*100);
        String f1score=df.format(eval.fMeasure(1)*100);
        System.out.println("\n==== STACKING (ANN+J48+AdaBoost+NB) ====");
        System.out.println("Accuracy: " + acc);
        System.out.println("Precision: " +pre);
        System.out.println("Recall: " +recall);
        System.out.println("F1 Score: " + f1score);
        Connection con=Dbconn.conn();
        Statement st1 = (Statement) con.createStatement();
		st1.executeUpdate("update tblanalysis set Accuracy_values='" + acc
				+ "',precision_values='" + pre + "',recall_values='"
				+ recall + "',fMeasure_values='" + f1score
				+ "' where id='3' and Algorithm_Name='HML'");
    }
}