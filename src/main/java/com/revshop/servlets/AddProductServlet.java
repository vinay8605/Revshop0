package com.revshop.servlets;

import com.revshop.dao.ProductDAO;
import com.revshop.dao.SellerDAO; 
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
@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {

    @SuppressWarnings("unused")
	private SellerDAO sellerDAO = new SellerDAO(); 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String image_url = request.getParameter("image_url"); 

        HttpSession session = request.getSession();
        Seller seller = (Seller) session.getAttribute("seller");

        if (seller != null) {
            int sellerId = seller.getSellerId(); 

            
            System.out.println("Seller ID: " + sellerId);

            Product product = new Product(name, description, price, image_url, sellerId); 
            ProductDAO productDAO = new ProductDAO();

            if (productDAO.addProduct(product)) {
                response.sendRedirect("sellerDashboard.jsp");
            } else {
                response.sendRedirect("addProduct.jsp?error=true");
            }
        } else {
            response.sendRedirect("sellerLogin.jsp");
        }
    }
}
