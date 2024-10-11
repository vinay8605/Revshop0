<%@ page import="java.util.List" %>
<%@ page import="com.revshop.models.Product" %>
<%@ page import="com.revshop.models.Review" %>
<%@ page import="com.revshop.dao.ProductDAO" %>
<%@ page import="com.revshop.models.CartItem" %>

<%
String productIdParam = request.getParameter("id");

    Product product = null;
    List<Review> reviews = null;

    if (productIdParam != null && !productIdParam.isEmpty()) {
        int productId = Integer.parseInt(productIdParam);
        System.out.println("Product ID: " + productId);
        ProductDAO productDAO = new ProductDAO();
        
        product = productDAO.getProductById(productId);
        if (product == null) {
            System.out.println("No product found with ID: " + productId);
        } else {
            System.out.println("Product found: " + product.getName());
        }
        reviews = productDAO.getReviewsByProductId(productId);
    }

%>

<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            max-width: 900px;
            width: 100%;
            padding: 20px;
            margin: 20px;
        }
        .product-details {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            gap: 20px;
        }
        .product-image {
            flex: 1;
            min-width: 300px;
        }
        .product-image img {
            max-width: 100%;
            border-radius: 10px;
        }
        .product-info {
            flex: 2;
            min-width: 300px;
        }
        .product-info h1 {
            margin-top: 0;
            font-size: 2rem;
            color: #333;
        }
        .product-info p {
            font-size: 1.1rem;
            margin: 10px 0;
        }
        .product-info strong {
            color: #e67e22;
        }
        .add-to-cart {
            margin-top: 20px;
        }
        .add-to-cart input[type="number"] {
            width: 60px;
            padding: 5px;
            margin-right: 10px;
        }
        .add-to-cart{
            background-color: #e67e22;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .add-to-cart:hover {
            background-color: #d35400;
        }
        .reviews, .review-form {
            margin-top: 40px;
        }
        .reviews h2, .review-form h2 {
            font-size: 1.5rem;
            color: #333;
        }
        .reviews ul {
            list-style-type: none;
            padding: 0;
        }
        .reviews li {
            background-color: #f4f4f4;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .review-form input, .review-form textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .review-form button {
            background-color: #27ae60;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .review-form button:hover {
            background-color: #2ecc71;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            color: #2980b9;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
        
    </style>
</head>
<body>

<div class="container">
<% if (product != null) { %>
    <div class="product-details">
        
        <div class="product-image">
        <img src="<%= product.getImageUrl() %>" alt="Product Image"  />
        </div>
        <div class="product-info">
        <h1><%= product.getName() %></h1>
        <p><strong>Price:</strong> $<%= product.getPrice() %></p>
        <p><strong>Description:</strong> <%= product.getDescription() %></p>
        
        
        <form action="cart" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productId" value="<%= product.getId() %>">
            <label for="quantity">Quantity:</label>
            <input type="number" name="quantity" min="1" value="1" required>
            <button type="submit" class="add-to-cart">Add to Cart</button>
        </form>
    </div>
    </div>

    <div class="reviews">
        <h2>User Reviews</h2>
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
    </div>

    <div class="review-form">
        <h2>Leave a Review</h2>
        <form action="addReview" method="post">
            <input type="hidden" name="productId" value="<%= product.getId() %>">
            <label for="userEmail">Your Email:</label>
            <input type="email" id="userEmail" name="userEmail" required><br>
            <label for="rating">Rating (1-5):</label>
            <input type="number" id="rating" name="rating" min="1" max="5" required><br>
            <label for="comment">Comment:</label><br>
            <textarea id="comment" name="comment" rows="4" cols="50" required></textarea><br>
            <button type="submit">Submit Review</button>
        </form>
    </div>

<% } else { %>
    <p>Product not found.</p>
<% } %>

<a href="productList.jsp">Back to Product List</a>
</div>

</body>
</html>
