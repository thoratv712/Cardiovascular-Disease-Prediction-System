package com.algo;
import com.connection.Dbconn;

import weka.attributeSelection.*;
import weka.core.Instances;

public class FeatureSelectionH {

    // 🔥 FCBF + ReliefF Hybrid
    public static Instances fcbfRelief(Instances data) throws Exception {

        // =========================
        // STEP 1: FCBF (SU-based)
        // =========================
        AttributeSelection fcbfSelector = new AttributeSelection();

        SymmetricalUncertAttributeEval suEval = new SymmetricalUncertAttributeEval();
        Ranker fcbfRanker = new Ranker();

        fcbfRanker.setNumToSelect(12); // keep more initially

        fcbfSelector.setEvaluator(suEval);
        fcbfSelector.setSearch(fcbfRanker);
        fcbfSelector.SelectAttributes(data);

        Instances fcbfData = fcbfSelector.reduceDimensionality(data);

        System.out.println("After FCBF: " + fcbfData.numAttributes());

        // =========================
        // STEP 2: ReliefF
        // =========================
        AttributeSelection reliefSelector = new AttributeSelection();

        ReliefFAttributeEval reliefEval = new ReliefFAttributeEval();
        Ranker reliefRanker = new Ranker();

        reliefRanker.setNumToSelect(8); // final best features

        reliefSelector.setEvaluator(reliefEval);
        reliefSelector.setSearch(reliefRanker);
        reliefSelector.SelectAttributes(fcbfData);

        Instances finalData = reliefSelector.reduceDimensionality(fcbfData);

        System.out.println("After ReliefF: " + finalData.numAttributes());
        Dbconn.saveARFF(finalData,Dbconn.FeatureSelectionH_model);

        return finalData;
    }
}