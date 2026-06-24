package com.connection;

import java.sql.*;
import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

public class Dbconn {

    public static String filepath =
        "C:\\Users\\Kamlesh\\Desktop\\ME Project Demo\\Code\\";

    public static String DB_model = filepath + "DB_model.csv";
    public static String Preprocessing_model = filepath + "Preprocessing_model.arff";
    public static String FeatureSelectionH_model = filepath + "FeatureSelectionH_model.arff";
    public static String PSO_Selected_Features_model = filepath + "PSO_Selected_Features_model.arff";

    // Auto-create folder on startup
    static {
        try {
            File dir = new File(filepath);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("Created directory: " + filepath);
            }
            System.out.println("Files will be saved at: " + filepath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String rf_acc = "";
    public static String rf_pre = "";
    public static String rf_recall = "";
    public static String rf_f1_score = "";

    public static String SVM_acc = "";
    public static String SVM_pre = "";
    public static String SVM_recall = "";
    public static String SVM_f1_score = "";

    public static String hml_acc = "";
    public static String hml_pre = "";
    public static String hml_recall = "";
    public static String hml_f1_score = "";

    public Dbconn() throws SQLException {
    }

    public static String configuration_matrix_values_RF() {
        try {
            Connection con = Dbconn.conn();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM tblanalysis WHERE Algorithm_Name='RF'");
            if (rs.next()) {
                rf_acc      = rs.getString(3);
                rf_pre      = rs.getString(4);
                rf_recall   = rs.getString(5);
                rf_f1_score = rs.getString(6);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rf_acc;
    }

    public static String configuration_matrix_values_SVM() {
        try {
            Connection con = Dbconn.conn();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM tblanalysis WHERE Algorithm_Name='SVM'");
            if (rs.next()) {
                SVM_acc      = rs.getString(3);
                SVM_pre      = rs.getString(4);
                SVM_recall   = rs.getString(5);
                SVM_f1_score = rs.getString(6);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SVM_acc;
    }

    public static String configuration_matrix_values_HML() {
        try {
            Connection con = Dbconn.conn();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM tblanalysis WHERE Algorithm_Name='HML'");
            if (rs.next()) {
                hml_acc      = rs.getString(3);
                hml_pre      = rs.getString(4);
                hml_recall   = rs.getString(5);
                hml_f1_score = rs.getString(6);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hml_acc;
    }

    public static void saveARFF(Instances data, String path) throws Exception {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(path));
        saver.writeBatch();
        System.out.println("ARFF saved at: " + path);
    }

    public static Instances loadARFF(String path) throws Exception {
        DataSource source = new DataSource(path);
        Instances data = source.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);
        return data;
    }

    public static Connection conn() throws Exception {
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3307/26_cardiovascular_disease_dataset",
            "root", "admin");
        return con;
    }

    public static void processData() {
    }
}