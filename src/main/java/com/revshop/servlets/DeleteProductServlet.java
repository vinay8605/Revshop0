package com.revshop.servlets;

import com.revshop.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        String productIdStr = request.getParameter("productId");
	        if (productIdStr == null || productIdStr.isEmpty()) {
	            response.sendRedirect("sellerDashboard.jsp?error=ProductIdMissing");
	            return;
	        }

	        int productId = Integer.parseInt(productIdStr);

	        ProductDAO productDAO = new ProductDAO();
	        productDAO.deleteProduct(productId); 

	        response.sendRedirect("sellerDashboard.jsp?success=ProductDeleted");
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        response.sendRedirect("sellerDashboard.jsp?error=InvalidProductId");
	    }
	}

}

