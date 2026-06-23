package com.algo;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;

public class DataLoader {

    public static Instances loadCSV(String path) throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(path));
        Instances data = loader.getDataSet();

        // Set class index (last column = target)
        data.setClassIndex(data.numAttributes() - 1);

        return data;
    }
}