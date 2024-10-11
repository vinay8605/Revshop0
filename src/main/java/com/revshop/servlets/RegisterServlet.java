package com.revshop.servlets;

import com.revshop.dao.UserDAO;
import com.revshop.models.User;

import jakarta.servlet.ServletException;  
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



@SuppressWarnings("serial")
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String firstName = request.getParameter("firstName");
	    String lastName = request.getParameter("lastName");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    User newUser = new User(firstName, lastName, email, password);
	    UserDAO userDAO = new UserDAO();

	    System.out.println("Attempting to register user: " + newUser);
	    
	    

	    if (userDAO.registerUser(newUser)) {
	        System.out.println("Registration successful for user: " + email);
	        response.sendRedirect("login.jsp"); 
	    } else {
	        System.out.println("Registration failed for user: " + email);
	        response.sendRedirect("register.jsp?error=true"); 
	    }
	    
	}

}
