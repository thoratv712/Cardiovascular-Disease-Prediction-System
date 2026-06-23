package com.Classification;

import weka.core.*;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.unsupervised.attribute.*;
import weka.classifiers.Classifier;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;

import java.io.File;
import java.util.Scanner;

public class PredictUserInput {

    public static void main(String[] args) throws Exception {

        // 🔥 1. LOAD CSV DATA
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("G:\\ME2025-2026\\2026 cs G12 ME Spcoe\\Code\\Cardiovascular_Disease_Dataset.csv"));
        Instances data = loader.getDataSet();

        data.setClassIndex(data.numAttributes() - 1);

     // 🔥 Convert class FIRST
        NumericToNominal numToNom = new NumericToNominal();
        numToNom.setOptions(new String[]{"-R", "last"});
        numToNom.setInputFormat(data);
        data = Filter.useFilter(data, numToNom);

        // 🔥 Remove patientid
        Remove remove = new Remove();
        remove.setAttributeIndices("1");
        remove.setInputFormat(data);
        data = Filter.useFilter(data, remove);

        // 🔥 Normalize
        Normalize norm = new Normalize();
        norm.setInputFormat(data);
        data = Filter.useFilter(data, norm);

        // 🔥 3. BASE MODELS

        MultilayerPerceptron ann = new MultilayerPerceptron();
        ann.setLearningRate(0.3);
        ann.setMomentum(0.2);
        ann.setTrainingTime(300);
        ann.setHiddenLayers("a");

        J48 j48 = new J48();
        j48.setConfidenceFactor(0.25f);
        j48.setMinNumObj(2);

        AdaBoostM1 ada = new AdaBoostM1();
        ada.setClassifier(new J48());
        ada.setNumIterations(50);

        NaiveBayes nb = new NaiveBayes();

        // 🔥 META MODEL
        Logistic meta = new Logistic();

        // 🔥 STACKING
        Stacking stack = new Stacking();
        Classifier[] baseModels = {ann, j48, ada, nb};

        stack.setClassifiers(baseModels);
        stack.setMetaClassifier(meta);


        // 🔥 4. TRAIN
        stack.buildClassifier(data);

        System.out.println("✅ Model trained successfully!");

        // 🔥 5. CREATE INPUT STRUCTURE (13 features)
        FastVector attrs = new FastVector();

        attrs.addElement(new Attribute("age"));
        attrs.addElement(new Attribute("gender"));
        attrs.addElement(new Attribute("chestpain"));
        attrs.addElement(new Attribute("restingBP"));
        attrs.addElement(new Attribute("serumcholestrol"));
        attrs.addElement(new Attribute("fastingbloodsugar"));
        attrs.addElement(new Attribute("restingrelectro"));
        attrs.addElement(new Attribute("maxheartrate"));
        attrs.addElement(new Attribute("exerciseangia"));
        attrs.addElement(new Attribute("oldpeak"));
        attrs.addElement(new Attribute("slope"));
        attrs.addElement(new Attribute("noofmajorvessels"));

        FastVector classVal = new FastVector(2);
        classVal.addElement("0");
        classVal.addElement("1");

        attrs.addElement(new Attribute("target", classVal));

        Instances dataset = new Instances("TestInstance", attrs, 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // 🔥 6. USER INPUT
        Scanner sc = new Scanner(System.in);
        double[] values = new double[dataset.numAttributes()];

        System.out.print("Age: "); values[0] = sc.nextDouble();
        System.out.print("Gender: "); values[1] = sc.nextDouble();
        System.out.print("Chest Pain: "); values[2] = sc.nextDouble();
        System.out.print("Resting BP: "); values[3] = sc.nextDouble();
        System.out.print("Cholesterol: "); values[4] = sc.nextDouble();
        System.out.print("Fasting Sugar: "); values[5] = sc.nextDouble();
        System.out.print("Rest ECG: "); values[6] = sc.nextDouble();
        System.out.print("Max HR: "); values[7] = sc.nextDouble();
        System.out.print("Exercise Angina: "); values[8] = sc.nextDouble();
        System.out.print("Oldpeak: "); values[9] = sc.nextDouble();
        System.out.print("Slope: "); values[10] = sc.nextDouble();
        System.out.print("Vessels: "); values[11] = sc.nextDouble();

        values[12] = Utils.missingValue();

        Instance inst = new DenseInstance(1.0, values);
        inst.setDataset(dataset);
        dataset.add(inst);

        // 🔥 7. PREDICT
        double result = stack.classifyInstance(dataset.instance(0));
        double[] prob = stack.distributionForInstance(dataset.instance(0));

        String prediction = dataset.classAttribute().value((int) result);

        // 🔥 8. OUTPUT
        System.out.println("\n=== RESULT ===");

        if (prediction.equals("1")) {
            System.out.println("⚠️ Heart Disease Detected");
        } else {
            System.out.println("✅ No Heart Disease");
        }

        System.out.println("\nProbability:");
        System.out.println("No Disease: " + prob[0]);
        System.out.println("Disease: " + prob[1]);
    }
}