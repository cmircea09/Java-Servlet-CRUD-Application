package com.signup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.signup.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Signup")
public class Signup extends HttpServlet 
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		 Connection con = DatabaseConnection.initializeDatabase(); 
		 PreparedStatement st = con.prepareStatement("insert into login values(?, ?)"); 
		 st.setString(1, request.getParameter("uname"));
		 st.setString(2, request.getParameter("pass")); 
		 st.executeUpdate(); 
		 st.close(); 
		 con.close(); 

		 PrintWriter out = response.getWriter(); 
		 out.println("<html><body><b>Successfully Inserted" + "</b></body></html>"); 
		 response.sendRedirect("index.jsp");
	}
}

