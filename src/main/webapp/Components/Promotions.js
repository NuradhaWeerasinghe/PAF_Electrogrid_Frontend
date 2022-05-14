
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
	var status = validatePromotionForm(); 
	if (status != true) 
	 { 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
		 return; 
	 } 
	// If valid------------------------
	var type = ($("#hidPromoIdSave").val() == "") ? "POST" : "PUT"; 
	 $.ajax( 
	 { 
		 url : "Promotions_API", 
		 type : type, 
		 data : $("#formPromotions").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
	 		onPromotionsSaveComplete(response.responseText, status); 
	 	 } 
	 }); 
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
	$("#hidPromoIdSave").val($(this).data("promotionId")); 
	$("#subject").val($(this).closest("tr").find('td:eq(0)').text()); 
	$("#description").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#fromDate").val($(this).closest("tr").find('td:eq(2)').text()); 
	$("#toDate").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#conditions").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#created_date").val($(this).closest("tr").find('td:eq(5)').text()); 
});

$(document).on("click", ".btnRemove", function(event) 
{ 
	 $.ajax( 
	 { 
		 url : "Promotions_API", 
		 type : "DELETE", 
		 data : "promotionId=" + $(this).data("promotionId"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 	onPromotionDeleteComplete(response.responseText, status); 
		 } 
	  }); 
});
// CLIENT-MODEL================================================================
function validatePromotionForm() 
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
	// From Date-------------------------------
	if ($("#fromDate").val().trim() == "") 
	 { 
		return "Insert From Date"; 
	 } 

	// To Date------------------------
	if ($("#toDate").val().trim() == "") 
	 { 
	 	return "Insert To Date"; 
	 } 
	 
	 // Conditions ------------------------
	if ($("#conditions").val().trim() == "") 
	 { 
	 	return "Insert Conditions"; 
	 } 
	 
	 // created_date------------------------
	if ($("#created_date").val().trim() == "") 
	 { 
	 	return "Insert created_date"; 
	 } 
	 
	return true; 
}

function onPromotionSaveComplete(response, status) 
{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
		 $("#alertSuccess").text("Successfully saved."); 
		 $("#alertSuccess").show(); 
		 $("#divPromotionsGrid").html(resultSet.data); 
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
	 $("#hidPromoIdSave").val(""); 
	 $("#formPromotions")[0].reset(); 
}


function onPromotionDeleteComplete(response, status) 
{ 
	if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 if (resultSet.status.trim() == "success") 
	 { 
		 $("#alertSuccess").text("Successfully deleted."); 
		 $("#alertSuccess").show(); 
		 $("#divPromotionsGrid").html(resultSet.data); 
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




