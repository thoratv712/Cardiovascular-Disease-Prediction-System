<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <!-- Basic -->
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <!-- Mobile Metas -->
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <meta name="viewport" content="initial-scale=1, maximum-scale=1">
   <!-- Site Metas -->
   <title>Life Care - Login</title>
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
   <!-- jQuery - required for custom.js -->
   <script src="jsnew/jquery-1.9.1.min.js"></script>
   <!-- Modernizer for Portfolio -->
   <script src="js/modernizer.js"></script>
</head>
<body class="clinic_version">
   <!-- LOADER -->
   <div id="preloader">
      <img class="preloader" src="images/loaders/heart-loading2.gif" alt="">
   </div>
   <!-- END LOADER -->
   <header>
      <div class="header-top wow fadeIn">
         <div class="container">
            <a class="navbar-brand" href="LoginPage.jsp">
               <img src="images/logo.png" alt="image">
            </a>
            <div class="right-header">
               <div class="header-info">
               </div>
            </div>
         </div>
      </div>
      <div class="header-bottom wow fadeIn">
         <div class="container">
            <nav class="main-menu">
               <div id="navbar" class="navbar-collapse collapse">
               </div>
            </nav>
            <div class="serch-bar">
               <div id="custom-search-input">
               </div>
            </div>
         </div>
      </div>
   </header>

   <div><br/><br/></div>
   <div><br/><br/></div>

   <div id="service" class="services wow fadeIn">
      <div class="container">
         <div class="row">
            <div>
               <div class="appointment-form" align="center">
                  <h3><span>+</span> Login</h3>
                  <div class="form">
                     <form action="loginpage" method="post">
                        <fieldset>

                           <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                              <div class="row">
                                 <div class="form-group">
                                    <input type="email"
                                           required="required"
                                           placeholder="Email Address"
                                           id="email"
                                           name="email"/>
                                 </div>
                              </div>
                           </div>

                           <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                              <div class="row">
                                 <div class="form-group">
                                    <input type="password"
                                           id="password"
                                           required="required"
                                           name="password"
                                           placeholder="Your Password"/>
                                 </div>
                              </div>
                           </div>

                           <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                              <div class="row">
                                 <div class="form-group">
                                    <div class="center">
                                       <button type="submit">Login</button>
                                    </div>
                                 </div>
                              </div>
                           </div>

                        </fieldset>
                     </form>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- end section -->

   <!-- end section -->
   <footer id="footer" class="footer-area wow fadeIn">
      <div class="container">
         <div class="row">
            <div class="col-md-4">
               <div class="logo padding">
                  <a href=""><img src="images/logo.png" alt=""></a>
                  <p>Cardiovascular Disease Prediction
                     using Hybrid Machine Learning.</p>
               </div>
            </div>
            <div class="col-md-4">
               <div class="footer-info padding">
                  <h3>CONTACT US</h3>
                  <p><i class="fa fa-map-marker" aria-hidden="true"></i>
                     SPCOE, Dumbarwadi, Otur</p>
                  <p><i class="fa fa-paper-plane" aria-hidden="true"></i>
                     info@spcoe.edu.in</p>
                  <p><i class="fa fa-phone" aria-hidden="true"></i>
                     (+91) 800 123 456</p>
               </div>
            </div>
            <div class="col-md-4">
               <div class="subcriber-info">
                  <h3>SYSTEM INFO</h3>
                  <p>Hybrid ML Framework for CVD Prediction</p>
                  <ul style="list-style:none; padding:0;
                             color:#aaa; font-size:13px;">
                     <li>&#10003; Random Forest</li>
                     <li>&#10003; Support Vector Machine</li>
                     <li>&#10003; XGBoost</li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
   </footer>

   <div class="copyright-area wow fadeIn">
      <div class="container">
         <div class="row">
            <div class="col-md-8">
               <div class="footer-text">
                  <p>&copy; 2026 Life Care. All Rights Reserved.</p>
               </div>
            </div>
            <div class="col-md-4">
               <div class="social">
                  <ul class="social-links">
                     <li><a href=""><i class="fa fa-rss"></i></a></li>
                     <li><a href=""><i class="fa fa-facebook"></i></a></li>
                     <li><a href=""><i class="fa fa-twitter"></i></a></li>
                     <li><a href=""><i class="fa fa-google-plus"></i></a></li>
                     <li><a href=""><i class="fa fa-youtube"></i></a></li>
                     <li><a href=""><i class="fa fa-pinterest"></i></a></li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- end copyrights -->
   <a href="#home" data-scroll class="dmtop global-radius">
      <i class="fa fa-angle-up"></i>
   </a>

   <!-- all plugins -->
   <script src="js/custom.js"></script>

   <!-- Hide preloader once page is ready -->
   <script>
   $(document).ready(function() {
       $('#preloader').fadeOut(500);
   });
   // Fallback - force hide after 2 seconds regardless
   setTimeout(function() {
       var p = document.getElementById('preloader');
       if (p) { p.style.display = 'none'; }
   }, 2000);
   </script>

</body>
</html>