<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.revshop.models.User" %> 
<%
    User user = (User) request.getAttribute("user"); 
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders</title>
    <style>
        

	body {
	    font-family: Arial, sans-serif; 
	    margin: 0; 
	    padding: 20px; 
	    background-color: #f4f4f4; 
	}
	
	h1 {
	    color: #333; 
	    text-align: center; 
	    margin-bottom: 20px; 
	}
	
	p {
	    color: #555; 
	    text-align: center; 
	    margin-bottom: 30px; 
	}
	.order-container {
	    background: white; 
	    padding: 20px; 
	    border-radius: 8px; 
	    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); 
	    margin-bottom: 20px; 
	}
	
	
	.order-item {
	    margin-bottom: 15px; 
	    padding: 10px; 
	    border: 1px solid #ddd; 
	    border-radius: 5px; 
	    background-color: #f9f9f9; 
	}
	
	.order-item h3 {
	    margin: 0; 
	    color: #007BFF; 
	}
	
	.btn {
	    display: inline-block; 
	    background-color: #007BFF; 
	    color: white; 
	    padding: 10px 20px; 
	    border-radius: 5px; 
	    text-decoration: none; 
	    transition: background-color 0.3s; 
	    margin: 0 auto; 
	    text-align: center; 
	}
	
	.btn:hover {
	    background-color: #0056b3; 
	}
	
	@media (max-width: 600px) {
	    body {
	        padding: 10px; 
	    }
	    .order-item {
	        padding: 8px; 
	    }
	}

    </style>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
             const userId = '<%= user != null ? user.getUserId() : "" %>'; 
            if (!userId) {
                console.error("User ID is not available.");
                return; 
            }
            fetchOrders(userId);

            function fetchOrders(userId) {
                const requestBody = new URLSearchParams();
                requestBody.append('user_id', userId); 

                fetch('/Revshop/fetchOrders', {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: requestBody.toString() 
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    displayOrders(data);
                })
                .catch(error => {
                    console.error("Error fetching orders:", error);
                });
            }

            function displayOrders(orders) {
                const ordersContainer = document.getElementById("orders-container");
                ordersContainer.innerHTML = ''; 

                if (Array.isArray(orders) && orders.length === 0) {
                    const noOrdersMessage = document.createElement("p");
                    noOrdersMessage.textContent = "No orders found.";
                    ordersContainer.appendChild(noOrdersMessage);
                    return;
                }

                orders.forEach(order => {
                    const orderDiv = document.createElement("div");
                    orderDiv.className = "order-item";

                    const orderIdElement = document.createElement("h3");
                    orderIdElement.textContent = 'Order ID: ' + order.orderId;

                    const totalAmountElement = document.createElement("p");
                    totalAmountElement.textContent = 'Total Amount: Rs.' + order.totalAmount.toFixed(2);

                    const addressElement = document.createElement("p");
                    addressElement.textContent = 'Address: ' + order.deliveryAddress;

                    const paymentMethodElement = document.createElement("p");
                    paymentMethodElement.textContent = 'Payment Method: ' + order.paymentMethod;

                    
                    orderDiv.appendChild(orderIdElement);
                    orderDiv.appendChild(totalAmountElement);
                    orderDiv.appendChild(addressElement);
                    orderDiv.appendChild(paymentMethodElement);

                    ordersContainer.appendChild(orderDiv);
                });
            }
        });
    </script>
</head>
<body>
    <h1>Order Placed Successfully!</h1>
        <p>Thank you for your order. Your order has been placed successfully, and you will receive a confirmation email shortly.</p>
        <a href="<%= request.getContextPath() %>/productList.jsp" class="btn">Continue Shopping</a>
            
    <div id="orders-container"></div>
</body>
</html>
