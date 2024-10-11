package com.revshop.dao;

import com.revshop.models.Seller;
import com.revshop.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerDAO {

    public boolean registerSeller(Seller seller) {
        String query = "INSERT INTO sellers (business_name, email, password, business_details) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, seller.getBusinessName());
            pstmt.setString(2, seller.getEmail());
            pstmt.setString(3, seller.getPassword()); 
            pstmt.setString(4, seller.getBusinessDetails());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    public static Seller authenticateSeller(String email, String password) {
        String query = "SELECT * FROM sellers WHERE email = ? AND password = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password); 
            
            System.out.println("Query: " + query);
            System.out.println("Email: " + email + ", Password: " + password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Seller(
                        rs.getInt("seller_id"), 
                        rs.getString("business_name"),
                        rs.getString("email"),
                        rs.getString("password"), 
                        rs.getString("business_details")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null; 
    }


}
