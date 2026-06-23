<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Prediction History</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Prediction History</h2>
    <div style="margin-bottom:10px;">
        <a class="btn btn-primary" href="DownloadPredictions">Download CSV</a>
        <button class="btn btn-secondary" onclick="window.print()">Export/PDF (print)</button>
    </div>
    <table class="table table-sm table-bordered">
        <thead>
        <tr>
            <th>Timestamp</th><th>Age</th><th>Gender</th><th>Chestpain</th><th>RestingBP</th><th>Serum</th><th>Fasting</th><th>RestECG</th><th>MaxHR</th><th>Exercise</th><th>Oldpeak</th><th>Slope</th><th>Vessels</th><th>Result</th><th>Prob</th>
        </tr>
        </thead>
        <tbody>
        <% List<String[]> rows = (List<String[]>) request.getAttribute("rows");
           if (rows!=null) for (String[] r: rows) {
               out.println("<tr>");
               for (int i=0;i<r.length;i++) out.println("<td>"+r[i]+"</td>");
               out.println("</tr>");
           }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
