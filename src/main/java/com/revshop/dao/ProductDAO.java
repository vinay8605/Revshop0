package com.revshop.dao;

import com.revshop.models.Product;
import com.revshop.models.Review;
import com.revshop.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setImageUrl(resultSet.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setImageUrl(resultSet.getString("image_url"));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Review> getReviewsByProductId(int productId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("id"));
                review.setProductId(resultSet.getInt("product_id"));
                review.setUserEmail(resultSet.getString("userEmail")); 
                review.setRating(resultSet.getInt("rating"));
                review.setComment(resultSet.getString("comment"));
                reviews.add(review);
            }
            
            System.out.println("Number of reviews fetched: " + reviews.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (product_id, userEmail, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, review.getProductId());
            statement.setString(2, review.getUserEmail());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); 
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, image_url, seller_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getImageUrl());
            statement.setInt(5, product.getSellerId()); 
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, image_url = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getImageUrl());
            statement.setInt(5, product.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("Product update failed. No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product deletion failed. No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    
    public List<Product> getProductsBySellerId(int sellerId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE seller_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setInt(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


}
