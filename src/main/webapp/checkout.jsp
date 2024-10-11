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
    List<CartItem> cartItems = cartDAO.getCartItems(user_id);

    double grandTotal = 0;
    for (CartItem item : cartItems) {
        grandTotal += item.getTotalPrice();
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Order Summary</title>

    <style>
   	body {
    	font-family: Roboto, sans-serif;
    	background-color: #f4f4f4;
    	margin: 0;
    	padding: 20px;
	}

	#order-summary-container {
    	background-color: #fff;
    	border-radius: 8px;
    	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    	padding: 20px;
    	max-width: 600px;
    	margin: auto;
	}

	#order-summary {
    	text-align: center;
    	color: #333;
	}

	#order-items {
    	margin-bottom: 20px;
	}

	.order-item {
    	border-bottom: 1px solid #ddd;
    	padding: 10px 0;
	}

	.product-details {
    	display: flex;
    	flex-direction: column;
	}

	.product-name {
    	font-weight: bold;
    	font-size: 1.1em;
    	color: #007BFF;
	}

	.product-description {
    	color: #666;
	}

	.item-details {
    	color: #555;
	}

	#total-price {
	    font-weight: bold;
	    font-size: 1.2em;
	    text-align: right;
	    margin-top: 10px;
	}
	
	#proceed-btn {
	    background-color: #007BFF;
	    color: white;
	    border: none;
	    padding: 10px 20px;
	    border-radius: 5px;
	    cursor: pointer;
	    transition: background-color 0.3s;
	}

	#proceed-btn:hover {
	    background-color: #0056b3;
	}
	
	.modal {
	    display: none; 
	    position: fixed;
	    z-index: 1; 
	    left: 0;
	    top: 0;
	    width: 100%; 
	    height: 100%; 
	    overflow: auto; 
	    background-color: rgba(0, 0, 0, 0.5); 
	    align-items: center;
	    justify-content: center;
	}
	
	.modal-content {
	    background-color: #fefefe;
	    margin: auto;
	    padding: 20px;
	    border: 1px solid #888;
	    border-radius: 8px;
	    width: 90%;
	    max-width: 400px; 
	}
	
	.close-btn {
	    color: #aaa;
	    float: right;
	    font-size: 28px;
	    font-weight: bold;
	}
	
	.close-btn:hover,
	.close-btn:focus {
	    color: black;
	    text-decoration: none;
	    cursor: pointer;
	}
	
	textarea {
	    width: 100%;
	    padding: 10px;
	    border-radius: 5px;
	    border: 1px solid #ccc;
	    margin-bottom: 10px;
	}
	
	.radio-group {
	    margin-bottom: 10px;
	}
	
	.radio-group label {
	    display: block;
	    margin: 5px 0;
	}
	
	.place-order-btn {
	    background-color: #28a745; 
	    color: white;
	    border: none;
	    padding: 10px 15px;
	    border-radius: 5px;
	    cursor: pointer;
	    transition: background-color 0.3s;
	}
	
	.place-order-btn:hover {
	    background-color: #218838; 
	}
	
	@media (max-width: 600px) {
	    #order-summary-container {
	        padding: 15px;
	    }
	    
	    .modal-content {
	        width: 95%; 
	    }
	}
        
    </style>
    
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
</head>

<body>
    <div id="order-summary-container">
        <h2 id="order-summary">Order Summary</h2>
        <div id="order-items">
            <% for (CartItem item : cartItems) { %>
                <div class="order-item">
                    <div class="product-details">
                        <div class="product-name"><%= item.getProductName() %></div>
                        <div class="product-description">Description not available</div>
                        <div class="item-details">Price: Rs.<%= item.getProductPrice() %> x <%= item.getQuantity() %></div>
                        <div class="item-details">Total: Rs.<%= item.getTotalPrice() %></div>
                    </div>
                </div>
            <% } %>
        </div>
        <div id="total-price">Total Price: Rs.<%= grandTotal %></div>
        <button id="proceed-btn">Proceed to Checkout</button>
    </div>

    
    <div class="modal" id="address-modal">
        <div class="modal-content">
            <button class="close-btn">&times;</button>
            <h3>Checkout</h3>
            <textarea name="orderAddress" rows="4" placeholder="Enter your address here..."></textarea>
            <div class="radio-group">
                <label><input type="radio" name="paymentMethod" value="cod" checked> Cash on Delivery</label>
                <label><input type="radio" name="paymentMethod" value="online"> Online Payment</label>
            </div>
            <button class="place-order-btn" id="place-order">Place Order</button>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const modal = document.getElementById("address-modal");
            const placeOrderBtn = document.getElementById("place-order");

            document.getElementById("proceed-btn").addEventListener("click", () => {
                modal.style.display = "flex";
            });

            document.querySelector(".close-btn").addEventListener("click", () => {
                modal.style.display = "none";
            });

            placeOrderBtn.addEventListener("click", () => {
                confirmOrder();
            });
        });

        function confirmOrder() {
            const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
            const orderAddress = document.querySelector('textarea[name="orderAddress"]').value;

            
            if (paymentMethod === "cod") {
                const requestBody = new URLSearchParams();
                requestBody.append('user_id', '<%= user.getUserId() %>'); 
                requestBody.append('totalAmount', '<%= grandTotal %>'); 
                requestBody.append('orderAddress', orderAddress);
                requestBody.append('paymentMethod', paymentMethod);

                fetch('/Revshop/confirmOrder', {
                    method: 'POST', 
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, 
                    body: requestBody.toString()
                })
                .then(response => {
                    
                    if (!response.ok) {
                        
                        return response.text().then(text => {
                            console.error(`Error response: ${text}`); 
                            throw new Error(`Network response was not ok: ${response.status}`);
                        });
                    }
                 
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        alert("Order placed successfully!"); 
                        window.location.href = "/Revshop/ordersComponent.jsp"; 
                    } else {
                        alert("Order confirmation failed.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("An error occurred while processing your order. Please try again.");
                });
            }
 else {
               
                const options = {
                    "key": "rzp_test_37MbLXJMCUd3yL", 
                    "amount": <%= (int)(grandTotal * 100) %>, 
                    "currency": "INR", 
                    "name": "Revshop", 
                    "description": "Order Payment", 
                    "handler": function (response) {
                        
                        fetch('/Revshop/confirmOrder', {
                            method: 'POST',
                            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                            body: new URLSearchParams({
                                user_id: '<%= user.getUserId() %>',
                                paymentMethod: 'online', 
                                paymentId: response.razorpay_payment_id, 
                                totalAmount: '<%= grandTotal %>',
                                orderAddress: orderAddress
                            })
                        })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok ' + response.statusText);
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.success) {
                                alert("Order placed successfully!");
                                window.location.href = "/Revshop/ordersComponent.jsp";
                            } else {
                                alert("Order confirmation failed.");
                            }
                        })
                        .catch(error => {
                            console.error("Error:", error);
                            alert("An error occurred while processing your order. Please try again.");
                        });

                    },
                    "prefill": {
                        "name": "<%= user.getFirstName() %>", 
                        "email": "<%= user.getEmail() %>" 
                    },
                    "theme": {
                        "color": "#3399cc" 
                    }
                };
                const rzp1 = new Razorpay(options); 
                rzp1.open();
            }
        }

    </script>
</body>
</html>
