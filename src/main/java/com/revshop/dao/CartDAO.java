package com.revshop.dao;

import com.revshop.models.CartItem;
import com.revshop.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    
	public void addCartItem(int user_id, int productId, int quantity) throws SQLException {
	    int cartId = getCartId(user_id); 

	    if (cartId == -1) {
	        cartId = createCart(user_id);
	    }

	    if (isProductInCart(cartId, productId)) {
	        updateCartItemQuantity(cartId, productId, quantity);
	    } else {
	        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity,user_id) VALUES (?, ?, ?, ?)";
	        try (Connection connection = DBConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	             
	            statement.setInt(1, cartId); 
	            statement.setInt(2, productId);
	            statement.setInt(3, quantity);
	            statement.setInt(4, user_id);
	            statement.executeUpdate();
	            System.out.println("Product added to cart: User ID: " + user_id + ", Product ID: " + productId + ", Quantity: " + quantity);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

    public void removeCartItem(int user_id, int productId) {
        int cartId = getCartId(user_id); 
        if (cartId == -1) {
            return; 
        }

        String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartId);
            statement.setInt(2, productId);
            statement.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> getCartItems(int user_id) {
        List<CartItem> items = new ArrayList<>();
        int cartId = getCartId(user_id); 
        if (cartId == -1) {
            return items; 
        }

        String sql = "SELECT ci.id, ci.cart_id, ci.product_id, ci.quantity, p.price, p.name " +
                     "FROM cart_items ci " +
                     "JOIN products p ON ci.product_id = p.id " +
                     "WHERE ci.cart_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartItem item = new CartItem();
                item.setId(resultSet.getInt("id"));
                item.setCartId(cartId);
                item.setProductId(resultSet.getInt("product_id"));
                item.setQuantity(resultSet.getInt("quantity"));
                item.setProductPrice(resultSet.getDouble("price"));
                item.setProductName(resultSet.getString("name")); 
                item.setTotalPrice(item.getQuantity() * item.getProductPrice()); 

                items.add(item); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public int getCartId(int user_id) {
        String sql = "SELECT id FROM carts WHERE user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    public int createCart(int user_id) throws SQLException {
        String sql = "INSERT INTO carts (user_id) VALUES (?)";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, user_id);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        }
        throw new SQLException("Failed to create a new cart for user ID: " + user_id);
    }

    private boolean isProductInCart(int cartId, int productId) {
        String sql = "SELECT id FROM cart_items WHERE cart_id = ? AND product_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartId);
            statement.setInt(2, productId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void updateCartItemQuantity(int cartId, int productId, int quantity) {
        String sql = "UPDATE cart_items SET quantity = quantity + ? WHERE cart_id = ? AND product_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantity);
            statement.setInt(2, cartId);
            statement.setInt(3, productId);
            statement.executeUpdate();
            System.out.println("Updated quantity for Product ID: " + productId + " in Cart ID: " + cartId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    
    public int getCartIdByUserId(int user_id) throws SQLException {
        String sql = "SELECT id FROM carts WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");  
                } else {
                    return createCart(user_id);
                }
            }
        }
    }
    
    public void clearCart(int userId) {
        String deleteCartItemsSQL = "DELETE FROM cart WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteCartItemsSQL)) {

            preparedStatement.setInt(1, userId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }
    
}

