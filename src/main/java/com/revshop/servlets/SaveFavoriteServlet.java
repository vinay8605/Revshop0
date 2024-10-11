package com.revshop.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import com.revshop.dao.FavoriteDAO;
import com.revshop.models.User;

@SuppressWarnings("serial")
@WebServlet("/saveFavorite")  
public class SaveFavoriteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int user_id = user.getUserId();
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        FavoriteDAO favoriteDAO = new FavoriteDAO();
        String message;

        try {
            if (favoriteDAO.isFavorite(user_id, productId)) {
                message = "This product is already in your favorites.";
            } else {
                favoriteDAO.saveFavorite(user_id, productId);
                message = "Product added to favorites successfully!";
            }

            request.setAttribute("favoriteMessage", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("productList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); 
            request.setAttribute("favoriteMessage", "Unable to save favorite.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("productList.jsp");
            dispatcher.forward(request, response);
        }
    }
}
