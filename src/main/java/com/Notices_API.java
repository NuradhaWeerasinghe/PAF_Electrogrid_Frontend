package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Notices_API
 */
@WebServlet("/Notices_API")
public class Notices_API extends HttpServlet {
	Interruptions intrObj = new Interruptions();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notices_API() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String output = intrObj.createInterruption( 
				request.getParameter("subject"), 
				request.getParameter("description"),
				request.getParameter("area"), 
				request.getParameter("time"), 
				request.getParameter("date"), 
				request.getParameter("created_date")
		); 
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request); 
		String output = intrObj.updateInterruption( 
				paras.get("hidIntteruptIdSave").toString(), 
				paras.get("subject").toString(), 
				paras.get("description").toString(), 
				paras.get("area").toString(),
				paras.get("time").toString(), 
				paras.get("date").toString(), 
				paras.get("created_date").toString());  
				response.getWriter().write(output);
	}

	private static Map getParasMap(HttpServletRequest request ) 
	{ 
	 Map<String, String> map = new HashMap<String, String>(); 
	try
	 { 
		Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
		String queryString = scanner.hasNext() ? 
			scanner.useDelimiter("\\A").next() : ""; 
		scanner.close(); 
		String[] params = queryString.split("&"); 
		for (String param : params) 
		{ 
			String[] p = param.split("="); 
			map.put(p[0], p[1]); 
		} 
	 } 
	catch (Exception e) 
	 { 
	 } 
	return map; 
	}
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request); 
		String output = intrObj.deleteInterruptions(paras.get("interruption_id").toString()); 
		response.getWriter().write(output); 
	}

}
