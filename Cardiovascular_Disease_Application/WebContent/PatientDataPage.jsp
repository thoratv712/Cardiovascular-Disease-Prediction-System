<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.connection.Dbconn" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>

<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <title>Life Care - Data Upload</title>
   <link rel="shortcut icon" href="images/fevicon.ico.png" type="image/x-icon" />
   <link rel="stylesheet" href="css/bootstrap.min.css">
   <link rel="stylesheet" href="style.css">
   <link rel="stylesheet" href="css/colors.css">
   <link rel="stylesheet" href="css/versions.css">
   <link rel="stylesheet" href="css/responsive.css">
   <link rel="stylesheet" href="css/custom.css">
   <script src="js/modernizer.js"></script>

   <style>
      .upload-box {
         background: white;
         border-radius: 12px;
         padding: 40px;
         box-shadow: 0 4px 20px rgba(0,0,0,0.1);
         max-width: 600px;
         margin: 0 auto;
      }
      .upload-box h3 {
         color: #1565C0;
         margin-bottom: 25px;
         text-align: center;
      }
      .upload-box input[type="file"] {
         width: 100%;
         padding: 10px;
         border: 2px dashed #1565C0;
         border-radius: 8px;
         margin-bottom: 20px;
      }
      .upload-btn {
         width: 100%;
         padding: 12px;
         background: #1565C0;
         color: white;
         border: none;
         border-radius: 8px;
         font-size: 15px;
         font-weight: bold;
         cursor: pointer;
      }
      .upload-btn:hover { background: #0D47A1; }
      .info-box {
         background: #E3F2FD;
         border-radius: 8px;
         padding: 15px;
         margin-top: 20px;
         font-size: 13px;
         color: #444;
      }
   </style>
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
                     <i class="fa fa-bars" aria-hidden="true"></i>
                  </button>
               </div>

               <div id="navbar" class="navbar-collapse collapse">
                  <ul class="nav navbar-nav">
                     <li>
                        <a href="PatientHomePage.jsp">Home</a>
                     </li>
                     <li class="active">
                        <a href="PatientDataPage.jsp">Data Upload</a>
                     </li>
                     <li>
                        <a href="preprocess">Pre-process</a>
                     </li>
                     <li>
                        <a href="FeatureSelection">Feature Selection</a>
                     </li>
                     <li>
                        <a href="Classification_process">Classification</a>
                     </li>
                     <li>
                        <a href="AnalysisPage.jsp">Analysis</a>
                     </li>
                     <li>
                        <a href="PredictRisk"
                           style="color:#FFD700;font-weight:bold;">
                           Prediction
                        </a>
                     </li>
                     <li>
                        <a href="loginpage?action=logout">Logout</a>
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
                  📤 Upload Patient Dataset
               </h2>
               <p style="color:#666;">
                  Upload CSV file containing cardiovascular
                  patient records
               </p>
            </div>
         </div>

         <div class="upload-box">
            <h3>📋 Select Dataset File</h3>

            <form enctype="multipart/form-data"
                  action="Datasetupload" method="post">

               <input type="file"
                      name="txt_search"
                      id="txt_search"
                      accept=".csv"
                      required/>

               <button type="submit" class="upload-btn">
                  ⬆ Upload Dataset
               </button>
            </form>

            <div class="info-box">
               <strong>📌 File Format Required:</strong><br/>
               CSV file with columns: patientid, age, gender,
               chestpain, restingBP, serumcholestrol,
               fastingbloodsugar, restingrelectro,
               maxheartrate, exerciseangia, oldpeak,
               slope, noofmajorvessels, target
            </div>
         </div>

         <div style="text-align:center; margin-top:30px;">
            <a href="preprocess"
               style="display:inline-block;
                      padding:10px 25px;
                      background:#2E7D32;
                      color:white;
                      border-radius:6px;
                      text-decoration:none;
                      font-weight:bold;">
               Next: Pre-process Data &rarr;
            </a>
         </div>

         <div><br/><br/></div>

      </div>
   </div>

   <footer id="footer" class="footer-area wow fadeIn">
      <div class="container">
         <div class="row">
            <div class="col-md-6">
               <div class="logo padding">
                  <a href="">
                     <img src="images/logo.png" alt="">
                  </a>
                  <p>Cardiovascular Disease Prediction
                     using Hybrid Machine Learning.</p>
               </div>
            </div>
            <div class="col-md-6">
               <div class="footer-info padding">
                  <h3>CONTACT</h3>
                  <p>
                     <i class="fa fa-map-marker"></i>
                     SPCOE, Dumbarwadi, Otur
                  </p>
                  <p>
                     <i class="fa fa-paper-plane"></i>
                     info@spcoe.edu.in
                  </p>
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

   <script src="js/custom.js"></script>
</body>
</html>