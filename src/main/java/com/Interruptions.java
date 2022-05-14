package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Interruptions {
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
	

	//Insert interruptions details to DB table
	public String createInterruption(String subject,String description,String area,String time,String date,String created_date )
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
			String query = " insert into interruptions (`Interruption_id`,`subject`,`description`,`area`,`time`, `date`, `created_date`)"
								+ " values (?, ?, ?, ?, ?, ?, ?)";
				
			PreparedStatement preparedStmt = con.prepareStatement(query);
				
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, subject);
			preparedStmt.setString(3, description);
			preparedStmt.setString(4, area);
			preparedStmt.setString(5, time);
			preparedStmt.setString(6, date);
			preparedStmt.setString(7, created_date);
				
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newInterruptions = readInterruption(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newInterruptions + "\"}"; 
		}
			
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Interruption Notice.\"}"; 
			System.err.println(e.getMessage());
		}
			
		return output;
	}
	
	//Read Interruption details
	public String readInterruption()
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
					 +"<th>Area</th><th>Time</th>" 
					 +"<th>Date</th><th>Date Created</th>" 
					 + "<th>Update</th><th>Remove</th></tr>"; 
			
			//SQL statement
			String query = "select * from interruptions";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
				
			// iterate through the rows in the result set
			while (rs.next()) {
				
				String interruption_id = Integer.toString(rs.getInt("Interruption_id")); 
				String subject = rs.getString("subject"); 
				String description = rs.getString("description"); 
				String area =  rs.getString("area"); 
				String time = rs.getString("time");
				String date = rs.getString("date");
				String created_date = rs.getString("created_date");
					
				 // Add into the html table
				
				 output += "<td>" + subject + "</td>"; 
				 output += "<td>" + description + "</td>"; 
				 output += "<td>" + area + "</td>"; 
				 output += "<td>" + time + "</td>"; 
				 output += "<td>" + date + "</td>"; 
				 output += "<td>" + created_date + "</td>"; 
				// buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
				 + "class='btnUpdate btn btn-secondary' data-interruption_id='" + interruption_id + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove' "
				 + "class='btnRemove btn btn-danger' data-interruption_id='" + interruption_id + "'></td></tr>"; 
					
			}
			con.close();
			 // Complete the html table
			 output += "</table>"; 
		}
			
		catch (Exception e)
		{
			e.printStackTrace();
			output = "Error while reading the Interruptions."; 
			System.err.println(e.getMessage()); 
		}
			
		return output;
	}
	
	//Update interruption details
	public String updateInterruption(String Interruption_id,String subject,String description,String area,String time,String date,String created_date) 
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
			String query = "UPDATE interruptions SET subject=?,description=?,area=?,time=?,date=?, created_date=? WHERE Interruption_id=?";
				
			PreparedStatement preparedStmt = con.prepareStatement(query);
				
			// binding values
			preparedStmt.setString(1, subject);
			preparedStmt.setString(2, description);
			preparedStmt.setString(3, area);
			preparedStmt.setString(4, time);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, created_date);
			preparedStmt.setInt(7, Integer.parseInt(Interruption_id));
				
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newInterruptions = readInterruption(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newInterruptions + "\"}"; 	
		}
			
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Interruption Notice.\"}"; 
			System.err.println(e.getMessage());
		}
			
		return output;
	}
	//Delete Interruption details
	public String deleteInterruptions(String Interruption_id)
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
			String query = "delete from interruptions where Interruption_id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(Interruption_id));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newInterruptions = readInterruption(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newInterruptions + "\"}"; 	
		}
		
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the Interruption Notice.\"}"; 
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
}
