<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.connection.Dbconn" %>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <title>Life Care - Analysis</title>
   <link rel="shortcut icon"
         href="images/fevicon.ico.png"
         type="image/x-icon"/>
   <link rel="stylesheet" href="css/bootstrap.min.css">
   <link rel="stylesheet" href="style.css">
   <link rel="stylesheet" href="css/colors.css">
   <link rel="stylesheet" href="css/versions.css">
   <link rel="stylesheet" href="css/responsive.css">
   <link rel="stylesheet" href="css/custom.css">

   <!-- jQuery local for custom.js compatibility -->
   <script src="jsnew/jquery-1.9.1.min.js"></script>
   <!-- Highcharts 4.2.7 - compatible with jQuery 1.9.1 -->
   <script src="https://code.highcharts.com/4.2.7/highcharts.js"></script>

   <style>
      .model-section {
         background: white;
         border-radius: 10px;
         padding: 25px;
         margin: 15px 0;
         box-shadow: 0 2px 15px rgba(0,0,0,0.1);
      }
      .model-section h4 {
         color: #1565C0;
         border-bottom: 2px solid #E3F2FD;
         padding-bottom: 10px;
         margin-bottom: 20px;
         font-size: 17px;
      }
      .export-btn {
         display: inline-block;
         padding: 10px 25px;
         background: #2E7D32;
         color: white;
         border-radius: 6px;
         text-decoration: none;
         font-size: 14px;
         font-weight: bold;
         margin: 10px 5px;
      }
      .export-btn:hover {
         background: #1B5E20;
         color: white;
         text-decoration: none;
      }
      .predict-btn-link {
         display: inline-block;
         padding: 10px 25px;
         background: #D32F2F;
         color: white;
         border-radius: 6px;
         text-decoration: none;
         font-size: 14px;
         font-weight: bold;
         margin: 10px 5px;
      }
      .predict-btn-link:hover {
         background: #B71C1C;
         color: white;
         text-decoration: none;
      }
      .perf-table {
         width: 100%;
         border-collapse: collapse;
         margin-top: 15px;
      }
      .perf-table th {
         background: #1565C0;
         color: white;
         padding: 10px 12px;
         text-align: left;
         font-size: 13px;
      }
      .perf-table td {
         padding: 10px 12px;
         border-bottom: 1px solid #eee;
         font-size: 14px;
      }
      .perf-table tr:hover { background: #F5F5F5; }
      .best-badge {
         background: #E8F5E9;
         color: #2E7D32;
         padding: 3px 10px;
         border-radius: 10px;
         font-size: 12px;
         font-weight: bold;
      }
      .metric-box {
         border-radius: 10px;
         padding: 20px;
         margin-bottom: 15px;
      }
      .metric-box h5 {
         margin-bottom: 12px;
         font-size: 16px;
      }
      .metric-box table {
         width: 100%;
         font-size: 14px;
      }
      .metric-box td {
         padding: 5px 0;
      }
      .nav-tabs .nav-link.active {
         background: #1565C0;
         color: white;
      }
      .tab-content {
         background: white;
         border-radius: 8px;
         padding: 20px;
         min-height: 100px;
      }
   </style>

<%
   // Load all metric values from DB
   Dbconn.configuration_matrix_values_RF();
   Dbconn.configuration_matrix_values_SVM();
   Dbconn.configuration_matrix_values_HML();

   String rf_acc  = (Dbconn.rf_acc != null &&
                    !Dbconn.rf_acc.trim().isEmpty())
                    ? Dbconn.rf_acc.trim() : "0";
   String rf_pre  = (Dbconn.rf_pre != null &&
                    !Dbconn.rf_pre.trim().isEmpty())
                    ? Dbconn.rf_pre.trim() : "0";
   String rf_rec  = (Dbconn.rf_recall != null &&
                    !Dbconn.rf_recall.trim().isEmpty())
                    ? Dbconn.rf_recall.trim() : "0";
   String rf_f1   = (Dbconn.rf_f1_score != null &&
                    !Dbconn.rf_f1_score.trim().isEmpty())
                    ? Dbconn.rf_f1_score.trim() : "0";

   String svm_acc = (Dbconn.SVM_acc != null &&
                    !Dbconn.SVM_acc.trim().isEmpty())
                    ? Dbconn.SVM_acc.trim() : "0";
   String svm_pre = (Dbconn.SVM_pre != null &&
                    !Dbconn.SVM_pre.trim().isEmpty())
                    ? Dbconn.SVM_pre.trim() : "0";
   String svm_rec = (Dbconn.SVM_recall != null &&
                    !Dbconn.SVM_recall.trim().isEmpty())
                    ? Dbconn.SVM_recall.trim() : "0";
   String svm_f1  = (Dbconn.SVM_f1_score != null &&
                    !Dbconn.SVM_f1_score.trim().isEmpty())
                    ? Dbconn.SVM_f1_score.trim() : "0";

   String hml_acc = (Dbconn.hml_acc != null &&
                    !Dbconn.hml_acc.trim().isEmpty())
                    ? Dbconn.hml_acc.trim() : "0";
   String hml_pre = (Dbconn.hml_pre != null &&
                    !Dbconn.hml_pre.trim().isEmpty())
                    ? Dbconn.hml_pre.trim() : "0";
   String hml_rec = (Dbconn.hml_recall != null &&
                    !Dbconn.hml_recall.trim().isEmpty())
                    ? Dbconn.hml_recall.trim() : "0";
   String hml_f1  = (Dbconn.hml_f1_score != null &&
                    !Dbconn.hml_f1_score.trim().isEmpty())
                    ? Dbconn.hml_f1_score.trim() : "0";
%>

   <script>
   $(document).ready(function() {

      var rfAcc  = parseFloat('<%=rf_acc%>')  || 0;
      var svmAcc = parseFloat('<%=svm_acc%>') || 0;
      var hmlAcc = parseFloat('<%=hml_acc%>') || 0;

      var rfPre  = parseFloat('<%=rf_pre%>')  || 0;
      var svmPre = parseFloat('<%=svm_pre%>') || 0;
      var hmlPre = parseFloat('<%=hml_pre%>') || 0;

      var rfRec  = parseFloat('<%=rf_rec%>')  || 0;
      var svmRec = parseFloat('<%=svm_rec%>') || 0;
      var hmlRec = parseFloat('<%=hml_rec%>') || 0;

      var rfF1   = parseFloat('<%=rf_f1%>')   || 0;
      var svmF1  = parseFloat('<%=svm_f1%>')  || 0;
      var hmlF1  = parseFloat('<%=hml_f1%>')  || 0;

      // Combined Metrics Chart
      $('#containergss').highcharts({
         chart: { type: 'column' },
         title: { text: 'Model Performance Comparison' },
         xAxis: {
            categories: ['Random Forest', 'SVM', 'XGBoost'],
            title: { text: 'Methods' },
            labels: {
               rotation: -45,
               style: { fontSize: '13px' }
            }
         },
         yAxis: {
            min: 0,
            max: 100,
            title: { text: 'Rate in %' }
         },
         tooltip: {
            pointFormat: '<b>{point.y:.2f}%</b>'
         },
         series: [{
            name: 'Accuracy',
            data: [rfAcc, svmAcc, hmlAcc]
         },{
            name: 'Precision',
            data: [rfPre, svmPre, hmlPre]
         },{
            name: 'Recall',
            data: [rfRec, svmRec, hmlRec]
         },{
            name: 'F-measure',
            data: [rfF1, svmF1, hmlF1]
         }]
      });

   });
   </script>
</head>

<body class="clinic_version">

   <header>
      <div class="header-top wow fadeIn">
         <div class="container">
            <a class="navbar-brand" href="PatientHomePage.jsp">
               <img src="images/logo.png" alt="image">
            </a>
         </div>
      </div>

      <div class="header-bottom wow fadeIn">
         <div class="container">
            <nav class="main-menu">
               <div class="navbar-header">
                  <button type="button"
                          class="navbar-toggle collapsed"
                          data-toggle="collapse"
                          data-target="#navbar"
                          aria-expanded="false"
                          aria-controls="navbar">
                     <i class="fa fa-bars"></i>
                  </button>
               </div>

               <div id="navbar" class="navbar-collapse collapse">
                  <ul class="nav navbar-nav">
                     <li>
                        <a href="PatientHomePage.jsp">Home</a>
                     </li>
                     <li>
                        <a href="PatientDataPage.jsp">
                           Data Upload
                        </a>
                     </li>
                     <li>
                        <a href="preprocess">Pre-process</a>
                     </li>
                     <li>
                        <a href="FeatureSelection">
                           Feature Selection
                        </a>
                     </li>
                     <li>
                        <a href="Classification_process">
                           Classification
                        </a>
                     </li>
                     <li class="active">
                        <a href="AnalysisPage.jsp">Analysis</a>
                     </li>
                     <li>
                        <a href="PredictRisk"
                           style="color:#FFD700;
                                  font-weight:bold;">
                           Prediction
                        </a>
                     </li>
                     <li>
                        <a href="loginpage?action=logout">
                           Logout
                        </a>
                     </li>
                  </ul>
               </div>
            </nav>
         </div>
      </div>
   </header>

   <div><br/><br/></div>
   <div><br/><br/></div>

   <div id="service" class="services wow fadeIn">
      <div class="container">

         <div class="row">
            <div class="col-md-12 text-center"
                 style="margin-bottom:25px;">
               <h2 style="color:#1565C0;">
                  📊 Model Performance Analysis
               </h2>
               <p style="color:#666; font-size:15px;">
                  Comparison of Random Forest,
                  SVM and XGBoost classifiers
               </p>
            </div>
         </div>

         <!-- Metric Cards -->
         <div class="row" style="margin-bottom:20px;">

            <div class="col-md-4">
               <div class="metric-box" style="background:#E3F2FD;">
                  <h5 style="color:#1565C0;">🌲 Random Forest</h5>
                  <table>
                     <tr><td>Accuracy:</td><td><strong><%=rf_acc%>%</strong></td></tr>
                     <tr><td>Precision:</td><td><strong><%=rf_pre%>%</strong></td></tr>
                     <tr><td>Recall:</td><td><strong><%=rf_rec%>%</strong></td></tr>
                     <tr><td>F1-Score:</td><td><strong><%=rf_f1%>%</strong></td></tr>
                  </table>
               </div>
            </div>

            <div class="col-md-4">
               <div class="metric-box" style="background:#F3E5F5;">
                  <h5 style="color:#6A1B9A;">⚙ SVM</h5>
                  <table>
                     <tr><td>Accuracy:</td><td><strong><%=svm_acc%>%</strong></td></tr>
                     <tr><td>Precision:</td><td><strong><%=svm_pre%>%</strong></td></tr>
                     <tr><td>Recall:</td><td><strong><%=svm_rec%>%</strong></td></tr>
                     <tr><td>F1-Score:</td><td><strong><%=svm_f1%>%</strong></td></tr>
                  </table>
               </div>
            </div>

            <div class="col-md-4">
               <div class="metric-box" style="background:#E8F5E9;">
                  <h5 style="color:#2E7D32;">🚀 XGBoost</h5>
                  <table>
                     <tr><td>Accuracy:</td><td><strong><%=hml_acc%>%</strong></td></tr>
                     <tr><td>Precision:</td><td><strong><%=hml_pre%>%</strong></td></tr>
                     <tr><td>Recall:</td><td><strong><%=hml_rec%>%</strong></td></tr>
                     <tr><td>F1-Score:</td><td><strong><%=hml_f1%>%</strong></td></tr>
                  </table>
               </div>
            </div>

         </div>

         <!-- Chart -->
         <div class="row">
            <div class="col-md-12">
               <div class="model-section">
                  <h4>📊 Model Performance Comparison</h4>
                  <div id="containergss"
                       style="width:100%; height:450px;">
                  </div>

                  <div style="text-align:center; margin-top:20px;">
                     <a href="AnalysisExport" class="export-btn">
                        📥 Export Metrics CSV
                     </a>
                     <a href="PredictRisk" class="predict-btn-link">
                        🫀 Go to Prediction Module
                     </a>
                  </div>
               </div>
            </div>
         </div>

         <!-- Performance Table -->
         <div class="row" style="margin-top:20px;">
            <div class="col-md-12">
               <div class="model-section">
                  <h4>📋 Detailed Performance Table</h4>
                  <table class="perf-table">
                     <tr>
                        <th>Model</th><th>Accuracy (%)</th>
                        <th>Precision (%)</th><th>Recall (%)</th>
                        <th>F1-Score (%)</th><th>Status</th>
                     </tr>
                     <tr>
                        <td><strong>🌲 Random Forest</strong></td>
                        <td><%=rf_acc%></td><td><%=rf_pre%></td>
                        <td><%=rf_rec%></td><td><%=rf_f1%></td>
                        <td><span class="best-badge">✓ Best Model</span></td>
                     </tr>
                     <tr>
                        <td><strong>⚙ SVM</strong></td>
                        <td><%=svm_acc%></td><td><%=svm_pre%></td>
                        <td><%=svm_rec%></td><td><%=svm_f1%></td>
                        <td>Good</td>
                     </tr>
                     <tr>
                        <td><strong>🚀 XGBoost</strong></td>
                        <td><%=hml_acc%></td><td><%=hml_pre%></td>
                        <td><%=hml_rec%></td><td><%=hml_f1%></td>
                        <td>Good</td>
                     </tr>
                  </table>
               </div>
            </div>
         </div>

         <!-- Tabs Section -->
         <div class="row" style="margin-top:20px;">
            <div class="col-md-12">
               <div class="model-section">
                  <ul class="nav nav-tabs" id="analysisTabs">
                     <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#performance">
                           Performance Metrics
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#confusion">
                           Confusion Matrices
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#roc">
                           ROC Curves
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#featimp">
                           Feature Importance
                        </a>
                     </li>
                  </ul>

                  <div class="tab-content">
                     <div class="tab-pane active" id="performance">
                        <p>Performance metrics shown above in table and chart.</p>
                     </div>
                     <div class="tab-pane" id="confusion">
                        <p>Confusion matrices — coming soon.</p>
                     </div>
                     <div class="tab-pane" id="roc">
                        <p>ROC curves — coming soon.</p>
                     </div>
                     <div class="tab-pane" id="featimp">
                        <p>View feature importance in the
                           <a href="PredictRisk">Prediction module</a>
                           after running a prediction.</p>
                     </div>
                  </div>
               </div>
            </div>
         </div>

         <div><br/><br/></div>

      </div>
   </div>

   <footer id="footer" class="footer-area wow fadeIn">
      <div class="container">
         <div class="row">
            <div class="col-md-6">
               <div class="logo padding">
                  <a href=""><img src="images/logo.png" alt=""></a>
                  <p>Cardiovascular Disease Prediction
                     using Hybrid Machine Learning.</p>
               </div>
            </div>
            <div class="col-md-6">
               <div class="footer-info padding">
                  <h3>CONTACT</h3>
                  <p><i class="fa fa-map-marker"></i> SPCOE, Dumbarwadi, Otur</p>
                  <p><i class="fa fa-paper-plane"></i> info@spcoe.edu.in</p>
               </div>
            </div>
         </div>
      </div>
   </footer>

   <div class="copyright-area wow fadeIn">
      <div class="container">
         <p style="color:#aaa; padding:15px 0;">
            &copy; 2026 Life Care. All Rights Reserved.
         </p>
      </div>
   </div>

   <a href="#" class="dmtop global-radius">
      <i class="fa fa-angle-up"></i>
   </a>

   <!-- Note: removed all.js to fix exports error -->
   <script src="js/custom.js"></script>

</body>
</html>