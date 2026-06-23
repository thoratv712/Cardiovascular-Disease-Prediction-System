package com.algo;

import weka.core.Instances;

import java.util.Random;

import com.connection.Dbconn;

public class MainPipeline {

	public static void main(String[] args) throws Exception {

		// 1. Load CSV
		Instances data1 = DataLoader
				.loadCSV("G:\\ME2025-2026\\2026 cs G12 ME Spcoe\\Code\\Cardiovascular_Disease_Dataset.csv");
		// Convert class to nominal FIRST
		data1 = Preprocessing.Preprocessing_model(data1);

		// FeatureSelection
		Instances data2 = Dbconn.loadARFF(Dbconn.Preprocessing_model);
		// 🔍 Debug check
		System.out.println("Class Attribute: " + data2.classAttribute());

		data2 = FeatureSelectionH.fcbfRelief(data2);

		// PSO Optimization
		Instances data3 = Dbconn.loadARFF(Dbconn.FeatureSelectionH_model);

		data3 = PSOFeatureSelection.optimize(data3);

		// Train/Test Split (70/30)
		Instances data = Dbconn.loadARFF(Dbconn.PSO_Selected_Features_model);

		data.randomize(new Random(1));

		int trainSize = (int) (data.numInstances() * 0.7);
		int testSize = data.numInstances() - trainSize;

		Instances train = new Instances(data, 0, trainSize);
		Instances test = new Instances(data, trainSize, testSize);
		// Train & Evaluate
		ModelTrainer.train(train, test);
	}
}