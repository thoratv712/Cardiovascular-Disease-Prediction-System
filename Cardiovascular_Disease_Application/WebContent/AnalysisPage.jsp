<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.connection.Dbconn"%>
    
<!DOCTYPE html>
<html lang="en">
   <!-- Basic -->
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <!-- Mobile Metas -->
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <meta name="viewport" content="initial-scale=1, maximum-scale=1">
   <!-- Site Metas -->
   <title>Life Care - Responsive HTML5 Multi Page Template</title>
   <meta name="keywords" content="">
   <meta name="description" content="">
   <meta name="author" content="">
   <!-- Site Icons -->
   <link rel="shortcut icon" href="images/fevicon.ico.png" type="image/x-icon" />
   <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
   <!-- Bootstrap CSS -->
   <link rel="stylesheet" href="css/bootstrap.min.css">
   <!-- Site CSS -->
   <link rel="stylesheet" href="style.css">
   <!-- Colors CSS -->
   <link rel="stylesheet" href="css/colors.css">
   <!-- ALL VERSION CSS -->
   <link rel="stylesheet" href="css/versions.css">
   <!-- Responsive CSS -->
   <link rel="stylesheet" href="css/responsive.css">
   <!-- Custom CSS -->
   <link rel="stylesheet" href="css/custom.css">
   <!-- Modernizer for Portfolio -->
  <script type="text/javascript" src="jsnew/jquery-1.9.1.min.js"></script>
<script src="jsnew/highcharts.js"></script>
<script>
	
<%          

			Dbconn.configuration_matrix_values_RF();
		   Dbconn.configuration_matrix_values_SVM();
		   Dbconn.configuration_matrix_values_HML();
			
			
			
			%>
	$(function() {
		$('#containergss').highcharts(
				{
					chart : {
						type : 'column'
					},
					title : {
						text : 'Calculate Accuracy'
					},
					subtitle : {

					},
					xAxis : {
						title : {
							text : 'Methods'
						},
						categories : ['Random Forest','SVM','XGBoost' ],
						labels : {
							rotation : -45,
							style : {
								fontSize : '13px',
								fontFamily : 'Verdana, sans-serif'
							}
						}

					},
					yAxis : {
						title : {
							text : 'Rate in %'
						},

					},
					legend : {
						enabled : false
					},
					tooltip : {
						pointFormat : '<b>{point.y:1f}%</b>'
					},
					series: [{
			            name: 'Accuracy',
			            data: [<%=Dbconn.rf_acc%>,<%=Dbconn.SVM_acc%>,<%=Dbconn.hml_acc%>]
			        }, {
			            name: 'Precision',
			            data: [<%=Dbconn.rf_pre%>,<%=Dbconn.SVM_pre%>,<%=Dbconn.hml_pre%>]
			        }, {
			            name: 'Recall',
			            data: [<%=Dbconn.rf_recall%>,<%=Dbconn.SVM_recall%>,<%=Dbconn.hml_recall%>]
			        }, {
			            name: 'F-measure',
			            data: [<%=Dbconn.rf_f1_score%>,<%=Dbconn.SVM_f1_score%>,<%=Dbconn.hml_f1_score%>]
			        }]
				});
	});
</script>
  
  
   </head>
   <body class="clinic_version">
      
      <header>
         <div class="header-top wow fadeIn">
            <div class="container">
               <a class="navbar-brand" href="index.html"><img src="images/logo.png" alt="image"></a>
               
            </div>
         </div>
         <div class="header-bottom wow fadeIn">
            <div class="container">
               <nav class="main-menu">
                  <div class="navbar-header">
                     <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"><i class="fa fa-bars" aria-hidden="true"></i></button>
                  </div>
				  
                  <div id="navbar" class="navbar-collapse collapse">
                     <ul class="nav navbar-nav">
                        <li><a class="active" href="PatientHomePage.jsp">Home</a></li>
                        <li><a  data-scroll href="PatientDataPage.jsp">Data Upload</a></li>
                        <li><a data-scroll href="preprocess">Pre-process</a></li>
                        <li><a data-scroll href="FeatureSelection">Feature Selection</a></li>
                        <li><a data-scroll href="Classification_process">Classification</a></li>
                        <li><a data-scroll href="AnalysisPage.jsp">Analysis</a></li>
                          <li><a data-scroll href="loginpage">Logout</a></li>
                        
                     </ul>
                  </div>
               </nav>
               <div class="serch-bar">
                  <div id="custom-search-input">
                    
                  </div>
               </div>
            </div>
         </div>
      </header>
      <div >
      <br/>
      <br/>
           </div>
           <div >
      <br/>
      <br/>
           </div>
       <div id="service" class="services wow fadeIn" >
         <div class="container">
         
            <div class="row">
            
                <div >
                  <div class="appointment-form" align="center">
                
                   
                     <div class="form">
                        
                          <div id="containergss"
                                        style="min-width: 600px; height: 500px; max-width: 200px;"
                                        align="center"></div>

                          <div style="margin-top:20px;">
                              <button class="btn btn-sm btn-outline-primary" onclick="location.href='AnalysisExport'">Export Metrics (CSV)</button>
                          </div>

                          <ul class="nav nav-tabs" id="analysisTabs" role="tablist" style="margin-top:10px;">
                              <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#performance">Performance Metrics</a></li>
                              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#confusion">Confusion Matrices</a></li>
                              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#roc">ROC Curves</a></li>
                              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#featimp">Feature Importance</a></li>
                          </ul>

                          <div class="tab-content" style="margin-top:10px;">
                              <div class="tab-pane active" id="performance">Performance metrics will appear here.</div>
                              <div class="tab-pane" id="confusion">Confusion matrices will appear here.</div>
                              <div class="tab-pane" id="roc">ROC curves will appear here.</div>
                              <div class="tab-pane" id="featimp">Feature importance charts will appear here.</div>
                          </div>

                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <!-- end section -->
	  
	  <!-- doctor -->
	  
	 	  
	   
      
      </body>
</html>
