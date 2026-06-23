<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Predict Risk</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>New Patient Prediction</h2>

    <form action="PredictRisk" method="post">
        <div class="row">
            <div class="col-md-4 form-group">
                <label>Age</label>
                <input name="age" class="form-control" type="number" step="any" required>
            </div>
            <div class="col-md-4 form-group">
                <label>Gender (0=female,1=male)</label>
                <input name="gender" class="form-control" type="number" step="1" min="0" max="1" required>
            </div>
            <div class="col-md-4 form-group">
                <label>Chest Pain (0-3)</label>
                <input name="chestpain" class="form-control" type="number" step="any" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 form-group">
                <label>Resting BP</label>
                <input name="restingBP" class="form-control" type="number" step="any" required>
            </div>
            <div class="col-md-4 form-group">
                <label>Serum Cholestrol</label>
                <input name="serumcholestrol" class="form-control" type="number" step="any" required>
            </div>
            <div class="col-md-4 form-group">
                <label>Fasting Blood Sugar (0/1)</label>
                <input name="fastingbloodsugar" class="form-control" type="number" step="1" min="0" max="1" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 form-group">
                <label>Resting ECG (0-2)</label>
                <input name="restingrelectro" class="form-control" type="number" step="any" required>
            </div>
            <div class="col-md-4 form-group">
                <label>Max Heart Rate</label>
                <input name="maxheartrate" class="form-control" type="number" step="any" required>
            </div>
            <div class="col-md-4 form-group">
                <label>Exercise Angina (0/1)</label>
                <input name="exerciseangia" class="form-control" type="number" step="1" min="0" max="1" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 form-group">
                <label>Oldpeak</label>
                <input name="oldpeak" class="form-control" type="number" step="any" required>
            </div>
            <div class="col-md-4 form-group">
                <label>Slope (0-2)</label>
                <input name="slope" class="form-control" type="number" step="any" required>
            </div>
            <div class="col-md-4 form-group">
                <label>No. of Major Vessels</label>
                <input name="noofmajorvessels" class="form-control" type="number" step="any" required>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Predict</button>
    </form>

    <hr/>
    <% if (request.getAttribute("prediction") != null) { %>
        <div class="alert alert-info" style="margin-top:20px;">
            <h4>Risk Result: <%= request.getAttribute("prediction") %></h4>
            <p>Probability (disease): <b><%= request.getAttribute("probability") %></b></p>
            <p>Top contributing factors:</p>
            <ul>
                <% java.util.List<String> top = (java.util.List<String>) request.getAttribute("topFeatures");
                   if (top!=null) for (String f: top) { %>
                    <li><%= f %></li>
                <% } %>
            </ul>
        </div>
    <% } %>

</div>
</body>
</html>
