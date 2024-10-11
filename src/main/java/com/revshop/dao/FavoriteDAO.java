package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revshop.models.Product;
import com.revshop.utils.DBConnection;
import java.util.*;

public class FavoriteDAO {
    public String saveFavorite(int userId, int productId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String message = "";
        try {
            connection = DBConnection.getConnection(); 
            String sql = "SELECT COUNT(*) FROM fav WHERE userId = ? AND productId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                
                message = "Product is already in favorites.";
                return message;
            }
            String sql1 = "INSERT INTO fav (userId, productId) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            message = "Product added to favorites successfully.";
            
        } catch (SQLException e) {
            e.printStackTrace(); 
            message = "Error occurred while adding product to favorites.";
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public boolean isFavorite(int userId, int productId) {
        String query = "SELECT COUNT(*) FROM fav WHERE userId = ? AND productId = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public List<Product> getFavoriteProductsByUserId(int userId) {
        List<Product> favoriteProducts = new ArrayList<>();
        String query = "SELECT p.* FROM products p JOIN fav f ON p.id = f.productId WHERE f.userId = ?";

        System.out.println("Retrieving favorites for user_id: " + userId); 

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("image_url"),
                    rs.getInt("seller_id")
                );
                favoriteProducts.add(product);
            }

            System.out.println("Favorite Products Count: " + favoriteProducts.size()); 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteProducts;
    }
}
