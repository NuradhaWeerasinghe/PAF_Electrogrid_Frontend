<%@page import="com.Promotions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
if (request.getParameter("subject") != null) {
	Promotions promObj = new Promotions();
	String stsMsg = "";
	//Insert--------------------------
	if (request.getParameter("hidPromoIdSave") == "") {
		stsMsg = promObj.insertPromotion(request.getParameter("subject"), request.getParameter("description"),
		request.getParameter("fromdate"), request.getParameter("todate"), request.getParameter("conditions"),
		request.getParameter("created_date"));
	} else
		
	//Update----------------------
	{
		stsMsg = promObj.updatePromotion(request.getParameter("hidPromoIdSave"), request.getParameter("subject"),
		request.getParameter("description"), request.getParameter("fromdate"), request.getParameter("todate"),
		request.getParameter("conditions"), request.getParameter("created_date"));
	}
	session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidPromoIdDelete") != null) {
	Promotions promObj = new Promotions();
	String stsMsg = promObj.deletePromotion(request.getParameter("hidPromoIdDelete"));
	session.setAttribute("statusMsg", stsMsg);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Notice Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Promotions.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Notice Management</h1>
				<h2>Promotions</h2>
				<form id="formPromotions" name="formPromotions">
					Subject : <input id="subject" name="subject" type="text"
						class="form-control form-control-sm"> <br>
					Description: <input id="description" name="description" type="text"
						class="form-control form-control-sm"> <br> 
					From Date: <input id="fromdate" name="fromdate" type="text"
						class="form-control form-control-sm"> <br> 
					To Date: <input id="todate" name="todate" type="text"
						class="form-control form-control-sm"> <br> 
					Conditions: <input id="conditions" name="conditions" type="text"
						class="form-control form-control-sm"> <br> Created
					Created Date: <input id="created_date" name="created_date" type="text"
						class="form-control form-control-sm"> <br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
					<input type="hidden" id="hidPromoIdSave" name="hidPromoIdSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divPromotionsGrid">
					<%
					Promotions promObj = new Promotions();
					out.print(promObj.readPromotion());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
