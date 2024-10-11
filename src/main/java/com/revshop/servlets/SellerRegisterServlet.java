package com.revshop.servlets;

import com.revshop.dao.SellerDAO;

import com.revshop.models.Seller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/sellerRegister")
public class SellerRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String businessName = request.getParameter("businessName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String businessDetails = request.getParameter("businessDetails");

        Seller newSeller = new Seller(businessName, email, password, businessDetails);
        SellerDAO sellerDAO = new SellerDAO();

        if (sellerDAO.registerSeller(newSeller)) {
            response.sendRedirect("sellerLogin.jsp");
        } else {
            response.sendRedirect("sellerRegister.jsp?error=true");
        }
    }
}
