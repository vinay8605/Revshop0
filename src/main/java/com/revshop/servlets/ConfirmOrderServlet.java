package com.revshop.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/confirmOrder")
public class ConfirmOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    @SuppressWarnings("unused")
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;

        try {
            // Reading the parameters
            String userId = req.getParameter("user_id");
            String amount = req.getParameter("totalAmount");
            String orderAddress = req.getParameter("orderAddress");
            String paymentMethod = req.getParameter("paymentMethod");

            // Log the parameters to check what is received
            System.out.println("User ID: " + userId);
            System.out.println("Amount: " + amount);
            System.out.println("Order Address: " + orderAddress);
            System.out.println("Payment Method: " + paymentMethod);

            // Validating parameters
            if (userId == null || paymentMethod == null || amount == null || orderAddress == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"User ID, payment method, total amount, and order address are required.\"}");
                return; // Make sure to return after writing the response
            }

            double totalAmount = Double.parseDouble(amount.trim());

            // (Your order processing logic goes here)

            // If everything goes well
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"success\": true, \"message\": \"Order confirmed successfully!\"}");

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Failed to confirm the order: " + e.getMessage() + "\"}");
        } finally {
            closeConnection(connection);
        }
    }



    @SuppressWarnings("unused")
	private double retrievePrice(Map<String, Object> item) {
        Object priceObj = item.get("price");
        if (priceObj instanceof Double) {
            return (Double) priceObj;
        } else if (priceObj instanceof Integer) {
            return (Integer) priceObj;
        } else {
            throw new IllegalArgumentException("Price must be a number");
        }
    }

    @SuppressWarnings("unused")
	private int fetchSellerId(Connection connection, int productId) throws SQLException {
        String sellerIdSQL = "SELECT seller_id FROM Products WHERE id = ?";
        try (PreparedStatement sellerIdStatement = connection.prepareStatement(sellerIdSQL)) {
            sellerIdStatement.setInt(1, productId);
            ResultSet sellerIdResult = sellerIdStatement.executeQuery();
            if (sellerIdResult.next()) {
                return sellerIdResult.getInt("seller_id");
            } else {
                throw new IllegalArgumentException("Seller ID not found for product ID: " + productId);
            }
        }
    }

    @SuppressWarnings("unused")
	private void handleSQLException(Connection connection, SQLException e, HttpServletResponse resp) throws IOException {
        if (connection != null) {
            try {
                connection.rollback(); 
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        e.printStackTrace(); 
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close(); 
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
    }
}
