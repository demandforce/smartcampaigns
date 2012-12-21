<%@ page import="com.demandforce.EmailAlertService" %>
<%@ page import="com.demandforce.AlertBusiness" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Alert Box</title>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
</head>
<body id="alertBody" style="border: 1">
<div class="alert alert-block">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <% EmailAlertService emailAlterService = new EmailAlertService();
    AlertBusiness alertBusiness =  emailAlterService.getEmailAlterForBusiness("");
    %>

    <h4><%=alertBusiness.getMessageTitle()%></h4>
    <p><%= alertBusiness.getMessageText()%></p>
    <a id="action" href="<%=alertBusiness.getActionUrl()%>" target="_top">
        <%=alertBusiness.getActionTitle()%></a> or <a href="https://172.16.12.15/bp2/campaigns/promotion/step/createCustomCampaign.jsp?cat=1" target="_top">Create your own campaign</a>
</div>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/smart.js"></script>
</body>
</html>