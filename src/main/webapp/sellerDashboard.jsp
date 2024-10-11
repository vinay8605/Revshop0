<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.revshop.models.Product" %>
<%@ page import="com.revshop.dao.ProductDAO" %>
<%@ page import="com.revshop.models.Review" %>
<%@ page import="com.revshop.models.Seller" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession session1 = request.getSession(); 
    Seller seller = (Seller) session1.getAttribute("seller");

    if (seller == null) {
        response.sendRedirect("sellerLogin.jsp"); 
        return; 
    }

    int sellerId = seller.getSellerId();
    ProductDAO productDAO = new ProductDAO();
    List<Product> products = productDAO.getProductsBySellerId(sellerId);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Seller Dash Board</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/CSS/sellerdashboard.css">
    <style type="text/css">
    .logout-btn {
            background-color: #dc3545; /* Red background */
            color: white; /* White text */
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
            float: right; /* Align the button to the right */
            margin: 10px;
        }

        .logout-btn:hover {
            background-color: #c82333; /* Darker red on hover */
        }
    
	.edit-link {
    	color: #007BFF; 
    	text-decoration: none; 
    	padding: 8px 12px; 
    	border: 1px solid #007BFF; 
    	border-radius: 5px; 
    	transition: background-color 0.3s, color 0.3s; 
    	display: inline-block; 
	}

	.edit-link:hover {
    	background-color: #007BFF; 
    	color: white;
    	text-decoration: none; 
	}

    </style>
    <script type="text/javascript">
        function logout() {
        	alert("Are you sure to Logout from the session");
            window.location.href = "sellerLogin.jsp";
        }
    </script>
</head>
<body>
	<button class="logout-btn" onclick="logout()">Logout</button>
    <h1>Welcome, <%= seller.getBusinessName() %></h1>
    <% if (request.getParameter("success") != null) { %>
    <div style="color: green;"><%= request.getParameter("success") %></div>
<% } %>
    <h2>Your Products</h2>
    <table>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Image</th>
            <th>Actions</th>
        </tr>
        <%
            for (Product product : products) {
            	List<Review> reviews = productDAO.getReviewsByProductId(product.getId());
        %>
        <tr>
    <td><%= product.getName() %></td>
    <td><%= product.getDescription() %></td>
    <td><%= product.getPrice() %></td>
    <td><img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>" width="100"></td>
    <td>
        <a href="<%= request.getContextPath() %>/editProduct.jsp?productId=<%= product.getId() %>" class="edit-link">Edit</a>
        
        <form action="<%= request.getContextPath() %>/deleteProduct" method="post" style="display:inline;">
    <input type="hidden" name="productId" value="<%= product.getId() %>">
    <button type="submit">Delete</button>
</form>

    </td>
</tr>
        
        <tr>
            <td colspan="5">
                <h4>Product Reviews:</h4>
                <ul>
                    <% if (reviews != null && !reviews.isEmpty()) { %>
                        <% for (Review review : reviews) { %>
                            <li>
                                <strong><%= review.getUserEmail() %>:</strong>
                                <em>(Rating: <%= review.getRating() %>)</em><br>
                                <p><%= review.getComment() %></p>
                            </li>
                        <% } %>
                    <% } else { %>
                        <li>No reviews available for this product.</li>
                    <% } %>
                </ul>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <h2>Add New Product</h2>
    <form action="<%= request.getContextPath() %>/addProduct" method="post">
        <label for="name">Product Name:</label>
        <input type="text" name="name" required>
        
        <label for="description">Description:</label>
        <textarea name="description" required></textarea>
        
        <label for="price">Price:</label>
        <input type="number" name="price" step="0.01" required>
        
        <label for="image_url">Image URL:</label>
        <input type="text" name="image_url" required>

        <button type="submit">Add Product</button>
    </form>
</body>
</html>
