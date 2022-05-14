
$(document).ready(function() 
{ 
	if ($("#alertSuccess").text().trim() == "") 
	{ 
	 	$("#alertSuccess").hide(); 
	} 
	$("#alertError").hide(); 
}); 
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
	// Clear alerts---------------------
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide(); 
	// Form validation-------------------
	var status = validateInterruptionForm(); 
	if (status != true) 
	 { 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
		 return; 
	 } 
	// If valid------------------------
	var type = ($("#hidIntteruptIdSave").val() == "") ? "POST" : "PUT"; 
	 $.ajax( 
	 { 
		 url : "Notices_API", 
		 type : type, 
		 data : $("#formInterruptions").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
	 		onInterruptionSaveComplete(response.responseText, status); 
	 	 } 
	 }); 
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
	$("#hidIntteruptIdSave").val($(this).data("interruption_id")); 
	$("#subject").val($(this).closest("tr").find('td:eq(0)').text()); 
	$("#description").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#area").val($(this).closest("tr").find('td:eq(2)').text()); 
	$("#time").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#date").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#created_date").val($(this).closest("tr").find('td:eq(5)').text()); 
});

$(document).on("click", ".btnRemove", function(event) 
{ 
	 $.ajax( 
	 { 
		 url : "Notices_API", 
		 type : "DELETE", 
		 data : "interruption_id=" + $(this).data("interruption_id"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 	onInterruptionDeleteComplete(response.responseText, status); 
		 } 
	  }); 
});
// CLIENT-MODEL================================================================
function validateInterruptionForm() 
{ 
	// Subject
	if ($("#subject").val().trim() == "") 
	 { 
	 	return "Insert subject"; 
	 } 
	// Description 
	if ($("#description").val().trim() == "") 
	 { 
	 	return "Insert description"; 
	 } 
	// Area-------------------------------
	if ($("#area").val().trim() == "") 
	 { 
		return "Insert Area"; 
	 } 

	// Date------------------------
	if ($("#date").val().trim() == "") 
	 { 
	 	return "Insert Date"; 
	 } 
	 
	 // created_date------------------------
	if ($("#created_date").val().trim() == "") 
	 { 
	 	return "Insert created_date"; 
	 } 
	 
	return true; 
}

function onInterruptionSaveComplete(response, status) 
{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
		 $("#alertSuccess").text("Successfully saved."); 
		 $("#alertSuccess").show(); 
		 $("#divInterruptionsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
		 $("#alertError").text(resultSet.data); 
		 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
	 } 
	 $("#hidIntteruptIdSave").val(""); 
	 $("#formInterruptions")[0].reset(); 
}


function onInterruptionDeleteComplete(response, status) 
{ 
	if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 if (resultSet.status.trim() == "success") 
	 { 
		 $("#alertSuccess").text("Successfully deleted."); 
		 $("#alertSuccess").show(); 
		 $("#divInterruptionsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
		 $("#alertError").text(resultSet.data); 
		 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while deleting."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while deleting.."); 
		 $("#alertError").show(); 
	 } 
}




