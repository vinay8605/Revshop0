package com.revshop.servlets;

import com.revshop.dao.ProductDAO;
import com.revshop.models.Review;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/addReview")
public class AddReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("productId"));
        String userEmail = request.getParameter("userEmail");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        System.out.println("Product ID: " + productId);
        System.out.println("User Email: " + userEmail);
        System.out.println("Rating: " + rating);
        System.out.println("Comment: " + comment);

        Review review = new Review();
        review.setProductId(productId);
        review.setUserEmail(userEmail);
        review.setRating(rating);
        review.setComment(comment);

        ProductDAO productDAO = new ProductDAO();
        productDAO.addReview(review);

        response.sendRedirect("productDetails.jsp?id=" + productId);
    }

}
