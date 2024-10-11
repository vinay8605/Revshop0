package com.revshop.servlets;

import com.revshop.dao.CartDAO;
import com.revshop.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int user_id = user.getUserId();
        CartDAO cartDAO = new CartDAO();

        try {
            if ("add".equals(action)) {
                String productIdStr = request.getParameter("productId");
                String quantityStr = request.getParameter("quantity");

                if (productIdStr == null || productIdStr.isEmpty() || quantityStr == null || quantityStr.isEmpty()) {
                    request.setAttribute("errorMessage", "Product ID and quantity are required.");
                    request.getRequestDispatcher("productList.jsp").forward(request, response);
                    return;  
                }

                int productId = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(quantityStr);

                cartDAO.addCartItem(user_id, productId, quantity);

                response.sendRedirect("viewCart");
                return;  
            } else if ("remove".equals(action)) {
                String productIdStr = request.getParameter("productId");

                if (productIdStr == null || productIdStr.isEmpty()) {
                    request.setAttribute("errorMessage", "Product ID is required.");
                    request.getRequestDispatcher("viewCart.jsp").forward(request, response);
                    return; 
                }

                int productId = Integer.parseInt(productIdStr);
                cartDAO.removeCartItem(user_id, productId);

                response.sendRedirect("viewCart");
                return;  
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid product ID or quantity format.");
            request.getRequestDispatcher("productList.jsp").forward(request, response);
            return;  
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
