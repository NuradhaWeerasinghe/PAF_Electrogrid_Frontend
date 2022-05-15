package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Promotions {
	private Connection connect()
	{
		Connection con = null;
				
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notification_Management", "root", "");
		}
				
		catch (Exception e)
		{
			e.printStackTrace();
		}
				
		return con;
	}
	
	//Insert promotion  details
	
	public String insertPromotion(String subject,String description,String fromDate,String toDate,String conditions,String createdDate)
	{
		String output = "";
		try 
		{
			Connection con = connect();
				
			if (con == null)
			{
				return "Error while connecting to the database for inserting."; 
			}
				
			// create a prepared statement
			String query = " insert into promotions (`PromotionId`,`subject`,`description`,`fromDate`,`toDate`,`conditions`, `createdDate`)"
								+ " values (?, ?, ?, ?, ?, ?, ?)";
				
			PreparedStatement preparedStmt = con.prepareStatement(query);
				
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, subject);
			preparedStmt.setString(3, description);
			preparedStmt.setString(4, fromDate);
			preparedStmt.setString(5, toDate);
			preparedStmt.setString(6, conditions);
			preparedStmt.setString(7, createdDate);
				
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPromotions = readPromotion(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newPromotions + "\"}"; 
				
			output = "Promotion Notice created successfully";
		}
			
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Promotion Notice.\"}"; 
			System.err.println(e.getMessage());
		}
			
		return output;
	}
	
	//Read promotion details
	
	public String readPromotion()
	{
		String output = ""; 
			
		try
		{
			Connection con = connect();
				
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			 output = "<table border='1'><tr>" 
					 +"<th>Subject</th><th>Description</th>"
					 +"<th>From Date</th><th>To Date</th>" 
					 +"<th>Conditions</th><th>Date Created</th>" 
					 + "<th>Update</th><th>Remove</th></tr>"; 
			
			//SQL statement
			String query = "select * from promotions";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
				
			// iterate through the rows in the result set
			while (rs.next()) {
				
				String promotionId = Integer.toString(rs.getInt("PromotionId")); 
				String subject = rs.getString("subject"); 
				String description = rs.getString("description"); 
				String fromDate =  rs.getString("fromDate"); 
				String toDate = rs.getString("toDate");
				String conditions = rs.getString("conditions");
				String createdDate = rs.getString("createdDate");
				
				// Add into the html table
				
				output += "<td>" + subject + "</td>"; 
				output += "<td>" + description + "</td>"; 
				output += "<td>" + fromDate + "</td>"; 
				output += "<td>" + toDate + "</td>"; 
				output += "<td>" + conditions + "</td>"; 
				output += "<td>" + createdDate + "</td>"; 
				// buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
				+ "class='btnUpdate btn btn-secondary' data-promotionId='" + promotionId + "'></td>"
				+ "<td><input name='btnRemove' type='button' value='Remove' "
				+ "class='btnRemove btn btn-danger' data-promotionId='" + promotionId + "'></td></tr>"; 
					
					
			}
			con.close();
			// Complete the html table
			 output += "</table>"; 
		}
			
		catch (Exception e)
		{
			e.printStackTrace();
			output = "Error while reading the Promotions."; 
			System.err.println(e.getMessage());
		}
			
		return output;
	}
	//Update Promotions details
	public String updatePromotion(String PromotionId, String subject, String description,String fromDate,String toDate,String conditions ,String createdDate)
	{
		String output = "";
			
		try
		{
			Connection con = connect();
				
			if (con == null)
			{
				return "Error while connecting to the database for updating."; 
			}
				
			// create a prepared statement
			String query = "UPDATE promotions SET subject=?,description=?,fromDate=?,toDate=?,conditions=?,createdDate=? WHERE PromotionId=?";
				
			PreparedStatement preparedStmt = con.prepareStatement(query);
				
			// binding values
			preparedStmt.setString(1, subject);
			preparedStmt.setString(2, description);
			preparedStmt.setString(3, fromDate);
			preparedStmt.setString(4, toDate);
			preparedStmt.setString(5, conditions);
			preparedStmt.setString(6, createdDate);
			preparedStmt.setInt(7, Integer.parseInt(PromotionId));
				
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPromotions = readPromotion(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newPromotions + "\"}"; 	
				
		}
			
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Promotions Notice.\"}"; 
			System.err.println(e.getMessage());
		}
			
		return output;
	}
	//Delete Promotion details
	public String deletePromotion(String PromotionId)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting."; 
			}
			
			// create a prepared statement
			String query = "delete from promotions where PromotionId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PromotionId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPromotions = readPromotion(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newPromotions + "\"}"; 
		}
		
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the Promotion Notice.\"}"; 
			System.err.println(e.getMessage());
		}
		
		return output;
	}


}
