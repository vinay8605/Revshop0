package com.revshop.servlets;

import com.revshop.dao.ProductDAO;
import com.revshop.models.Product;
import com.revshop.models.Seller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        String productIdStr = request.getParameter("productId");
	        if (productIdStr == null || productIdStr.isEmpty()) {
	            response.sendRedirect("sellerDashboard.jsp?error=ProductIdMissing");
	            return;
	        }

	        int productId = Integer.parseInt(productIdStr);
	        String name = request.getParameter("name");
	        String description = request.getParameter("description");
	        double price = Double.parseDouble(request.getParameter("price"));
	        String image_url = request.getParameter("image_url");

	        HttpSession session = request.getSession();
	        Seller seller = (Seller) session.getAttribute("seller");
	        int sellerId = seller.getSellerId();

	        Product product = new Product(productId, name, description, price, image_url, sellerId);
	        System.out.println("Product ID: " + product.getId());
	        System.out.println("Product Name: " + product.getName());
	        System.out.println("Product Description: " + product.getDescription());
	        System.out.println("Product Price: " + product.getPrice());
	        System.out.println("Product Image URL: " + product.getImageUrl());

	        ProductDAO productDAO = new ProductDAO();
	        productDAO.updateProduct(product);

	        response.sendRedirect("sellerDashboard.jsp?success=ProductUpdated");
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        response.sendRedirect("sellerDashboard.jsp?error=InvalidProductId");
	    }
	}



}
