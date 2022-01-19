package employee.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import employee.model.Employee;

public class EmployeeDAO 
{      
    protected Connection getConnection() 
    {

        Connection connection = null;
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
            		"jdbc:mysql://localhost:3306/emp_management_sys?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            		"root",
            		"admin");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }

        return connection;
    }
   
    public void insertEmployee(Employee employee) throws SQLException 
    {
    	System.out.println("INSERT INTO employee" + "(name,email,country,role) VALUES " + "(?,?,?,?);");
        try (Connection connection = getConnection();
        	
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee" + "(name,email,country,role) VALUES " + "(?,?,?,?);")) 
        {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getCountry());
            preparedStatement.setString(4, employee.getRole());
            
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public Employee selectEmployee(int id) 
    {
        Employee employee = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id ,name,email,country,role FROM employee WHERE id=?");
        ) 
        {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) 
            {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String role=rs.getString("role");
                employee = new Employee(id, name, email, country,role);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Employee> selectAllEmployees() 
    {
    	
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee");
        ) 
        {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) 
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String role=rs.getString("role");
                employees.add(new Employee(id, name, email, country,role));
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return employees;
    }

    public boolean deleteEmployee(int id) throws SQLException 
    {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE id=?;");
        ) 
        {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateEmployee(Employee employee) throws SQLException 
    {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employee set name=?, email=? , country =?,role=? where id=?;")) 
        {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getCountry());
            preparedStatement.setString(4, employee.getRole());
            preparedStatement.setInt(5, employee.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        
        return rowUpdated;
        }
}
