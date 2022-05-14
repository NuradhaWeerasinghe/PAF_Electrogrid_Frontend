<%@page import="com.Interruptions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
if (request.getParameter("subject") != null) {
	Interruptions intrObj = new Interruptions();
	String stsMsg = "";
	//Insert--------------------------
	if (request.getParameter("hidIntteruptIdSave") == "") {
		stsMsg = intrObj.createInterruption(request.getParameter("subject"), request.getParameter("description"),
		request.getParameter("area"), request.getParameter("time"), request.getParameter("date"),
		request.getParameter("created_date"));
	} else//Update----------------------
	{
		stsMsg = intrObj.updateInterruption(request.getParameter("hidIntteruptIdSave"), request.getParameter("subject"),
		request.getParameter("description"), request.getParameter("area"), request.getParameter("time"),
		request.getParameter("date"), request.getParameter("created_date"));
	}
	session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidIntteruptIdDelete") != null) {
	Interruptions intrObj = new Interruptions();
	String stsMsg = intrObj.deleteInterruptions(request.getParameter("hidIntteruptIdDelete"));
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
<script src="Components/Interruptions.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Notice Management</h1>
				<h2>Interruptions</h2>
				<form id="formInterruptions" name="formInterruptions">
					Subject : <input id="subject" name="subject" type="text"
						class="form-control form-control-sm"> <br>
					Description: <input id="description" name="description" type="text"
						class="form-control form-control-sm"> <br> 
					Area: <input id="area" name="area" type="text"
						class="form-control form-control-sm"> <br> 
					Time: <input id="time" name="time" type="text"
						class="form-control form-control-sm"> <br> 
					Date: <input id="date" name="date" type="text"
						class="form-control form-control-sm"> <br> 
					Created	Date: <input id="created_date" name="created_date" type="text"
						class="form-control form-control-sm"> <br> 
					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> 
					<input type="hidden" id="hidIntteruptIdSave" name="hidIntteruptIdSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divInterruptionsGrid">
					<%
					Interruptions intrObj = new Interruptions();
					out.print(intrObj.readInterruption());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
