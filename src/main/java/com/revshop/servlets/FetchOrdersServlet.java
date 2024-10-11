package com.revshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.revshop.utils.DBConnection; 
import com.revshop.models.Orders;
import com.revshop.models.User; 

@WebServlet("/fetchOrders")
public class FetchOrdersServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String userId = request.getParameter("user_id");
        List<Orders> orders = new ArrayList<>();
        User user = null; 

        try (Connection conn = DBConnection.getConnection()) {
            String userSql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            userStmt.setString(1, userId);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                user = new User();
                user.setUserId(userRs.getInt("user_id"));
                user.setFirstName(userRs.getString("name"));
                user.setEmail(userRs.getString("email"));
                
            }

            String sql = "SELECT * FROM orders WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            StringBuilder jsonResponse = new StringBuilder();
            jsonResponse.append("{");

            if (user != null) {
                jsonResponse.append("\"user\":{");
                jsonResponse.append("\"userId\":").append(user.getUserId()).append(",");
                jsonResponse.append("\"name\":\"").append(user.getFirstName()).append("\",");
                jsonResponse.append("\"email\":\"").append(user.getEmail()).append("\"");
                jsonResponse.append("},");
            }

            jsonResponse.append("\"orders\":[");
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    jsonResponse.append(",");
                }
                first = false;

                jsonResponse.append("{");
                jsonResponse.append("\"orderId\":").append(rs.getInt("order_id")).append(",");
                jsonResponse.append("\"totalAmount\":").append(rs.getDouble("total_amount")).append(",");
                jsonResponse.append("\"deliveryAddress\":\"").append(rs.getString("delivery_address")).append("\",");
                jsonResponse.append("\"paymentMethod\":\"").append(rs.getString("payment_method")).append("\"");
                jsonResponse.append("}");
            }
            jsonResponse.append("]"); 
            jsonResponse.append("}"); 

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"Error fetching orders.\"}");
        } finally {
            out.close();
        }
    }
}
