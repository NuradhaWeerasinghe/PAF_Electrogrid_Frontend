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
 * Servlet implementation class Promotions_API
 */
@WebServlet("/Promotions_API")
public class Promotions_API extends HttpServlet {

	Promotions promObj = new Promotions();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Promotions_API() {
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
		String output = promObj.insertPromotion(  
				request.getParameter("subject"), 
				request.getParameter("description"),
				request.getParameter("fromDate"), 
				request.getParameter("toDate"), 
				request.getParameter("conditions"),
				request.getParameter("createdDate")
		);
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request); 
		String output = promObj.updatePromotion( 
				paras.get("hidPromoIdSave").toString(), 
				paras.get("subject").toString(), 
				paras.get("description").toString(), 
				paras.get("fromDate").toString(),
				paras.get("toDate").toString(), 
				paras.get("conditions").toString(), 
				paras.get("createdDate").toString());  
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
		String output = promObj.deletePromotion(paras.get("promotionId").toString()); 
		response.getWriter().write(output); 
	}

}
