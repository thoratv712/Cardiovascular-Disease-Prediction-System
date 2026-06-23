package com.algo;

import weka.core.Instances;
import weka.core.Attribute;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.util.ArrayList;
import java.util.Random;

import com.connection.Dbconn;

public class PSOFeatureSelection {

    static int particles = 10;
    static int iterations = 20;

    // FIXED SEED (SAME OUTPUT EVERY TIME)
    static Random rand = new Random(42);

    public static Instances optimize(Instances data) {

        int features = data.numAttributes() - 1;

        double[][] pos = new double[particles][features];
        double[][] vel = new double[particles][features];

        // Initialize particles (deterministic)
        for (int i = 0; i < particles; i++) {
            for (int j = 0; j < features; j++) {
                pos[i][j] = rand.nextDouble();
                vel[i][j] = rand.nextDouble();
            }
        }

        double[] gbest = pos[0].clone();

        // PSO ITERATIONS
        for (int iter = 0; iter < iterations; iter++) {

            for (int i = 0; i < particles; i++) {

                double fitness = fitness(pos[i]);

                if (fitness > fitness(gbest)) {
                    gbest = pos[i].clone();
                }
            }

            for (int i = 0; i < particles; i++) {
                for (int j = 0; j < features; j++) {

                    vel[i][j] = 0.5 * vel[i][j]
                            + 1.5 * rand.nextDouble() * (gbest[j] - pos[i][j]);

                    pos[i][j] += vel[i][j];

                    // Clamp between 0 and 1
                    if (pos[i][j] > 1) pos[i][j] = 1;
                    if (pos[i][j] < 0) pos[i][j] = 0;
                }
            }
        }

        Instances newData=applyMask(data, gbest);
       
        System.out.println("PSO Selected Features: " + newData.numAttributes());
        // APPLY FEATURE MASK
        return newData;
    }

    // Fitness (maximize selected features)
    private static double fitness(double[] p) {
        double sum = 0;
        for (double v : p) sum += v;
        return sum;
    }

    // Convert PSO vector → selected features
    private static Instances applyMask(Instances data, double[] gbest) {

        try {
            ArrayList<Integer> removeList = new ArrayList<>();

            for (int i = 0; i < gbest.length; i++) {
                if (gbest[i] < 0.5) {
                    removeList.add(i);
                }
            }

            // Convert to string indices
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < removeList.size(); i++) {
                sb.append(removeList.get(i) + 1);
                if (i < removeList.size() - 1) sb.append(",");
            }

           // if (sb.length() == 0) return data;

            Remove remove = new Remove();
            remove.setAttributeIndices(sb.toString());
            remove.setInputFormat(data);

            //newData
            data= Filter.useFilter(data, remove);

//            // Set class index again
//            newData.setClassIndex(newData.numAttributes() - 1);
//
//            System.out.println("PSO Selected Features: " + newData.numAttributes());
//
//            return newData;
            Dbconn.saveARFF(data,Dbconn.PSO_Selected_Features_model);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}