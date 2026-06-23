package com.algo;

import com.connection.Dbconn;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class Preprocessing {

	
	public static Instances Preprocessing_model(Instances data)
	{
		 try {
	            // Step 1: Convert class → nominal
	            data = convertClassToNominal(data);

	            // Step 2: Remove ID
	            data = removeID(data);

	            // Step 3: Normalize
	            data = normalize(data);
	            Dbconn.saveARFF(data,Dbconn.Preprocessing_model);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		 
	        return data;
	}
	
	
    // Convert class to NOMINAL (FIX)
    public static Instances convertClassToNominal(Instances data) throws Exception {
        NumericToNominal filter = new NumericToNominal();
        filter.setOptions(new String[]{"-R", "last"});
        filter.setInputFormat(data);
        return Filter.useFilter(data, filter);
    }

    // Remove patientid
    public static Instances removeID(Instances data) throws Exception {
        Remove remove = new Remove();
        remove.setAttributeIndices("1");
        remove.setInputFormat(data);
        return Filter.useFilter(data, remove);
    }

    // Normalize
    public static Instances normalize(Instances data) throws Exception {
        Normalize norm = new Normalize();
        norm.setInputFormat(data);
        return Filter.useFilter(data, norm);
    }
}