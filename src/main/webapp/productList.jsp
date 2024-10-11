<%@ page import="java.util.List" %>
<%@ page import="com.revshop.models.Product" %>
<%@ page import="com.revshop.dao.ProductDAO" %>

<%
    List<Product> productList = null;
    try {
        ProductDAO productDAO = new ProductDAO();
        productList = productDAO.getAllProducts(); 
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/CSS/productList.css?version=1.0">
    <style>

	body {
	    font-family: Arial, sans-serif;
	    margin: 0;
	    padding: 20px;
	    background: linear-gradient(to bottom, rgba(34, 34, 34, 0.9), rgba(54, 54, 54, 0.9)), url('your-background-image-url.jpg') no-repeat center center fixed;
	    background-size: cover; 
	    color: #f4f4f4; 
	}

	h1 {
	    color: #f9c74f; 
	    text-align: center;
	    margin-bottom: 30px;
	}

	a {
	    text-decoration: none;
	    color: #f9c74f; 
	}

	a:hover {
    	text-decoration: underline;
	}

	ul {
	    display: grid;
	    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
	    grid-gap: 20px;
	    list-style: none;
	    padding: 0;
	}

	li {
	    background-color: rgba(255, 255, 255, 0.9); 
	    border: 1px solid #ddd;
	    border-radius: 10px;
	    padding: 15px;
	    transition: box-shadow 0.3s, transform 0.3s;
	    text-align: center;
	}

	li:hover {
	    box-shadow: 0 12px 12px rgba(0, 238, 255, 0.8);
	    transform: translateY(-5px);
	}

	h2 {
	    font-size: 22px;
	    color: #333; 
	    margin: 10px 0;
	}

	p {
	    color: #666; 
	    margin: 5px 0;
	    font-size: 14px;
	}

	img {
	    width: 100%;
	    max-width: 200px;
	    height: auto;
	    margin-bottom: 10px;
	    border-radius: 8px;
	    transition: transform 0.3s;
	}

	img:hover {
    	transform: scale(1.05);
	}

	form {
    	margin: 10px 0;
	}

	input[type="number"] {
	    width: 60px;
	    padding: 7px;
	    margin-right: 10px;
	}

	button {
	    background-color: #28a745;
	    color: white;
	    border: none;
	    padding: 10px 15px;
	    border-radius: 5px;
	    cursor: pointer;
	    transition: background-color 0.3s;
	}

	button:hover {
    	background-color: #218838; 
	}

	.button-container {
	    display: flex;
	    justify-content: space-between; 
	    padding: 20px; 
	    position: relative; 
	}

	.view-favorites {
	    padding: 10px 20px;
	    background-color: #007BFF; 
	    color: white;
	    border-radius: 5px;
	    transition: background-color 0.3s;
	}

	.view-favorites:hover {
	    background-color: #0056b3; 
	    text-decoration: none;
	}

	.view-cart {
	    padding: 10px 20px;
	    background-color: #28a745; 
	    color: white;
	    border-radius: 5px;
	    transition: background-color 0.3s;
	}

	.view-cart:hover {
    	background-color: #218838; 
    	text-decoration: none;
	}


	a.view-details {
    	display: inline-block;
    	margin-top: 10px;
    	padding: 8px 15px;
    	background-color: #000; 
    	color: white; 
    	border-radius: 5px;
    	transition: background-color 0.3s, transform 0.3s;
	}

	a.view-details:hover {
		text-decoration: none;
    	background-color: #e5533d; 
    	transform: scale(1.05); 
	}
	.logout-btn {
            background-color: #dc3545; 
            color: white; 
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
            float: right; 
            margin: 10px;
        }

        .logout-btn:hover {
            background-color: #c82333; 
        }


    
    </style>
    <script type="text/javascript">
        function logout() {
        	alert("Are you sure to Logout from the session");
            window.location.href = "login.jsp";
        }
    </script>
    
</head>
<body>
	<button class="logout-btn" onclick="logout()">Logout</button>
    <h1>Available Products</h1>
    <div class="button-container">

	<a class="view-cart" href="userFavorites.jsp">View Favorite Products</a>
    <a class="view-cart" href="${pageContext.request.contextPath}/viewCart">View Cart</a>
    </div>
    

    
    <% 
        String favoriteMessage = (String) request.getAttribute("favoriteMessage"); 
        if (favoriteMessage != null) { 
    %>
        <script>
            alert("<%= favoriteMessage %>");
        </script>
    <% 
        } 
    %>

    <ul>
        <% if (productList != null && !productList.isEmpty()) { %>
            <% for (Product product : productList) { %>
                <li>
                    <h2><%= product.getName() %></h2>
                    <img src="<%= product.getImageUrl() %>" alt="Product Image" />
                    <p>Price: <%= product.getPrice() %></p>
                    <p>Description: <%= product.getDescription() %></p>

                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="add" />
                        <input type="hidden" name="productId" value="<%= product.getId() %>" />
                        <input type="number" name="quantity" value="1" min="1" required />
                        <button type="submit">Add to Cart</button>
                    </form>
                    
                    <form action="<%= request.getContextPath() %>/saveFavorite" method="post">
                        <input type="hidden" name="productId" value="<%= product.getId() %>">
                        <button type="submit">Save as Favorite</button>
                        
                    </form>

                    <a href="productDetails.jsp?id=<%= product.getId() %>" class="view-details">View Details</a>
                    
                </li>
            <% } %>
        <% } else { %>
            <li>No products available.</li>
        <% } %>
    </ul>
    
</body>
</html>
