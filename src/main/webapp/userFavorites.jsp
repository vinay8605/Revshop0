<%@ page import="java.util.List" %>
<%@ page import="com.revshop.models.Product" %>
<%@ page import="com.revshop.dao.FavoriteDAO" %>
<%@ page import="com.revshop.models.User" %>
<%@ page import="com.revshop.dao.ProductDAO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Favorite Products</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/CSS/userFavorites.css?version=1.0">
    <style>
    * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

body {
    font-family: Arial, sans-serif;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    position: relative;
    overflow: hidden;
    background: linear-gradient(135deg, rgba(34, 34, 34, 0.9), rgba(45, 45, 45, 0.9));
}

.container {
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 10px;
    padding: 30px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
    width: 90%;
    max-width: 600px;
    text-align: center;
}

h2 {
    margin-bottom: 20px;
    color: #333;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    padding: 10px;
    border-bottom: 1px solid #ddd;
}

li:last-child {
    border-bottom: none;
}

.no-favorites {
    color: #e74c3c;
    font-weight: bold;
}

a {
    text-decoration: none;
    color: #6a11cb;
}

a:hover {
    text-decoration: underline;
    color: #2575fc;
}

@media (max-width: 600px) {
    .container {
        padding: 20px;
    }

    li {
        padding: 8px;
    }
}
    </style>
    
    


</head>
<body>
    <div class="container">
        <%
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int user_id = user.getUserId();
            System.out.println("User ID: " + user_id); 
            FavoriteDAO favoriteDAO = new FavoriteDAO();
            List<Product> favoriteProducts = favoriteDAO.getFavoriteProductsByUserId(user_id);
        %>

        <h2>Your Favorite Products</h2>
        <ul>
        <% if (favoriteProducts != null && !favoriteProducts.isEmpty()) { %>
            <% for (Product product : favoriteProducts) { %>
                <li><%= product.getName() %>  <a href="productDetails.jsp?id=<%= product.getId() %>">View Details</a></li>
            <% } %>
        <% } else { %>
            <li class="no-favorites">No favorite products found.</li>
        <% } %>
        </ul>
    </div>
</body>
</html>
