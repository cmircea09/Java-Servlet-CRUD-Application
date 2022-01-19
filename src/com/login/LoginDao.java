package com.login;

import java.sql.*;
public class LoginDao
{
	public boolean check(String uname,String pass) throws SQLException 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("\"jdbc:mysql://localhost:3306/emp_management_sys?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC,","root", "admin");
		PreparedStatement st=con.prepareStatement("\"select * from login where uname=? and pass=?\"");
			st.setString(1, uname);
			st.setString(2, pass);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) 
			{
				return true;
			}	
		} 
		catch (ClassNotFoundException e) 
		{	
			e.printStackTrace();
		}
			
		return false;
	}
}
