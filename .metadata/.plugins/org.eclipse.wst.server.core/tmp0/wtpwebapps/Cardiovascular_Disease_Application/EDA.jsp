<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Exploratory Data Analysis</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="jsnew/jquery-1.9.1.min.js"></script>
    <script src="jsnew/highcharts.js"></script>
</head>
<body>
<div class="container">
    <h2>Exploratory Data Analysis</h2>

    <h4>Dataset Summary</h4>
    <table class="table table-bordered">
        <tr><th>Rows</th><td><%= request.getAttribute("rows") %></td></tr>
        <tr><th>Columns</th><td><%= request.getAttribute("cols") %></td></tr>
    </table>

    <h4>Missing Values</h4>
    <table class="table table-striped">
        <tr><th>Attribute</th><th>Missing Count</th></tr>
        <% Map<String,Integer> missing = (Map<String,Integer>) request.getAttribute("missing");
           if (missing!=null) for (Map.Entry<String,Integer> e: missing.entrySet()) { %>
            <tr><td><%= e.getKey() %></td><td><%= e.getValue() %></td></tr>
        <% } %>
    </table>

    <h4>Class Distribution</h4>
    <div id="classDist" style="height:300px; min-width:310px"></div>

    <h4>Correlation Matrix</h4>
    <table class="table table-condensed table-bordered">
        <tr>
            <th></th>
            <% List<String> features = (List<String>) request.getAttribute("features");
               if (features!=null) for (String f: features) { %>
                <th><%= f %></th>
            <% } %>
        </tr>
        <% double[][] corr = (double[][]) request.getAttribute("corr");
           if (corr!=null) for (int i=0;i<corr.length;i++) { %>
            <tr>
                <th><%= features.get(i) %></th>
                <% for (int j=0;j<corr[i].length;j++) { %>
                    <td><%= String.format("%.3f", corr[i][j]) %></td>
                <% } %>
            </tr>
        <% } %>
    </table>

    <h4>Feature Distributions</h4>
    <% Map<String,int[]> hist = (Map<String,int[]>) request.getAttribute("hist");
       Map<String,double[]> binEdges = (Map<String,double[]>) request.getAttribute("binEdges");
       if (hist!=null) {
           for (String f : hist.keySet()) {
               String divId = "hist_" + f.replaceAll("[^A-Za-z0-9]","_"); %>
               <div style="margin-top:20px;">
                   <h5><%= f %></h5>
                   <div id="<%=divId%>" style="height:250px; min-width:310px"></div>
                   <script>
                       (function(){
                           var counts = <%= java.util.Arrays.toString(hist.get(f)) %>;
                           var edges = <%= java.util.Arrays.toString(binEdges.get(f)) %>;
                           var labels = [];
                           for (var i=0;i<edges.length-1;i++) labels.push(edges[i].toFixed(2)+" - "+edges[i+1].toFixed(2));
                           Highcharts.chart('<%=divId%>', {
                               chart: { type: 'column' },
                               title: { text: '' },
                               xAxis: { categories: labels, title: { text: 'Bin' } },
                               yAxis: { title: { text: 'Count' } },
                               series: [{ name: '<%=f%>', data: counts }]
                           });
                       })();
                   </script>
               </div>
    <%     }
       }
    %>

</div>
</body>
</html>
