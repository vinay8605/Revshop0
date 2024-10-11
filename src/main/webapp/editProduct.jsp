<%@ page import="com.revshop.dao.ProductDAO" %>
<%@ page import="com.revshop.models.Product" %>

<%
    String productIdParam = request.getParameter("productId");

    if (productIdParam == null || productIdParam.isEmpty()) {
        response.sendRedirect("productList.jsp?error=ProductIdMissing");
        return;
    }

    try {
        int productId = Integer.parseInt(productIdParam);
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            response.sendRedirect("productList.jsp?error=ProductNotFound");
            return;
        }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <style>
        label {
            display: block;
            margin-bottom: 8px;
        }
        input, textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Edit Product</h1>

    <form action="<%= request.getContextPath() %>/editProduct" method="post">
    <input type="hidden" name="productId" value="<%= product.getId() %>">
    <label for="name">Product Name:</label>
    <input type="text" name="name" value="<%= product.getName() %>" required>
    
    <label for="description">Description:</label>
    <textarea name="description" required><%= product.getDescription() %></textarea>
    
    <label for="price">Price:</label>
    <input type="number" name="price" step="0.01" value="<%= product.getPrice() %>" required>
    
    <label for="image_url">Image URL:</label>
    <input type="text" name="image_url" value="<%= product.getImageUrl() %>" required>

    <button type="submit">Update Product</button>
</form>

</body>
</html>

<%
    } catch (NumberFormatException e) {
        response.sendRedirect("productList.jsp?error=InvalidProductId");
    }
%>
