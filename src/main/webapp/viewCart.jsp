<%@ page import="java.util.List" %>
<%@ page import="com.revshop.models.CartItem" %>
<%@ page import="com.revshop.dao.CartDAO" %>
<%@ page import="com.revshop.models.User" %>


<%
    CartDAO cartDAO = new CartDAO();
User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}

int user_id = user.getUserId();
System.out.println("User ID: " + user_id); 

    List<CartItem> cartItems = cartDAO.getCartItems(user_id);
    double grandTotal = 0;
    
    Integer cartId = (Integer) session.getAttribute("cartId");
    if (cartId == null) {
        cartId = 0; 
    }
%>


<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/CSS/viewCart.css?version=1.0">
    
    
</head>
<body>
    <h1>Your Cart</h1>

    <% 
        String errorMessage = (String) request.getAttribute("errorMessage"); 
        if (errorMessage != null) { 
    %>
        <div class="error"><%= errorMessage %></div>
    <% 
        } 
    %>
    
    

    <table>
        <thead>
            <tr>
                <th>Product ID</th>
                <th>Quantity</th>
                <th>Price per Item</th>
                <th>Total Price</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% for (CartItem item : cartItems) { %>
                <tr>
                    <td><%= item.getProductId() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td><%= item.getProductPrice() %></td>
                    <td><%= item.getTotalPrice() %></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post" onsubmit="return confirm('Are you sure you want to remove this item from your cart?');">
                            <input type="hidden" name="action" value="remove" />
                            <input type="hidden" name="productId" value="<%= item.getProductId() %>" />
                            <button type="submit">Remove</button>
                        </form>
                    </td>
                </tr>
                <%
                    grandTotal += item.getTotalPrice();
                %>
            <% } %>
        </tbody>
    </table>

    <h3>Grand Total: <%= grandTotal %></h3>
    
    <div id="cart-summary">
    <button id="checkout-btn">Checkout</button>
    </div>
    
    <a href="${pageContext.request.contextPath}/productList.jsp">Continue Shopping</a>
    
    <script>
        document.getElementById("checkout-btn").addEventListener("click", () => {
            window.location.href = 'checkout.jsp'; // Redirect to the order summary page
        });
    </script>
</body>
</html>
