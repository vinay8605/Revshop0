package com.revshop.servlets;

import com.revshop.dao.SellerDAO;
import com.revshop.models.Seller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/sellerLogin")
public class SellerLoginServlet extends HttpServlet {

	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    System.out.println("Email: " + email);
	    System.out.println("Password: " + password);

	    SellerDAO sellerDAO = new SellerDAO();
	    Seller seller = sellerDAO.authenticateSeller(email, password);

	    if (seller != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("seller", seller);
	        response.sendRedirect(request.getContextPath() + "/sellerDashboard.jsp");
	        System.out.println("Login Successful");
	    } else {
	        response.sendRedirect("sellerLogin.jsp?error=true");
	        System.out.println("Login Failed");
	    }
	}

}
