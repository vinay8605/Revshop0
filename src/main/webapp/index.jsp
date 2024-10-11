<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to RevShop</title>
    <script src="<%= request.getContextPath() %>/JS/index.js"></script>
    <style>
    body {
    font-family: 'Arial', sans-serif;
    background-color: #f9f9f9;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

header {
    background-color: #333;
    color: #fff;
    padding: 15px 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
}

.logo img {
    height: 60px;
}

nav ul {
    list-style: none;
    display: flex;
    gap: 20px;
}

nav ul li a {
    color: white;
    text-decoration: none;
    font-size: 16px;
    transition: color 0.3s ease;
}

nav ul li a:hover {
    color: #4CAF50;
}

.welcome-section {
    text-align: center;
    padding: 40px 20px;
    background-color: #f4f4f4;
    flex: 1;
}

.welcome-section h1 {
    font-size: 48px;
    margin-bottom: 10px;
    color: #333;
}

.welcome-section p {
    font-size: 18px;
    color: #666;
    margin-bottom: 30px;
}

.action-buttons {
    margin-top: 20px;
}

.action-buttons form {
    display: inline-block;
    margin: 0 15px;
}

.btn {
    background-color: #4CAF50;
    color: white;
    padding: 12px 24px;
    font-size: 16px;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.btn:hover {
    background-color: #45a049;
}

.product-card img {
    width: 100%;
    border-radius: 5px;
    margin-bottom: 10px;
}

footer {
    background-color: #333;
    color: white;
    text-align: center;
    padding: 15px 0;
}

footer p {
    margin: 0;
}


.carousel {
    position: relative;
    overflow: hidden;
    max-width: 800px;
    margin: 0 auto;
}

.carousel-inner {
    display: flex;
    transition: transform 0.5s ease;
}

.carousel-item {
    display: flex;
    min-width: 100%;
    justify-content: center;
}

.product-card {
    margin: 0 10px;
    background-color: #fff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    border-radius: 5px;
    padding: 20px;
    text-align: center;
}

.product-card img {
   
    border-radius: 5px;
    margin-bottom: 10px;
}

.carousel-control {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(255, 255, 255, 0.7);
    border: none;
    border-radius: 50%;
    padding: 10px;
    cursor: pointer;
    z-index: 1;
}

.prev {
    left: 10px;
}

.next {
    right: 10px;
}

    
    </style>
</head>
<body>
    <header>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/revshop_logo.jpg" alt="RevShop Logo">
        </div>
        <nav>
            <ul>
                <li><a href="home.jsp">Home</a></li>
                <li><a href="producs.jsp">Shop</a></li>
                <li><a href="about.jsp">About</a></li>
                <li><a href="contact.jsp">Contact</a></li>
            </ul>
        </nav>
    </header>

    <section class="welcome-section">
        <h1>Welcome to RevShop</h1>
        <p>Your one-stop destination for all your shopping needs. Find the best deals, shop your favorite products, and experience the ultimate convenience.</p>
        <h2>Please Log In or Register</h2>

        <div class="action-buttons">
            <form action="login.jsp" method="get">
                <button type="submit" class="btn">User Log In</button>
            </form>
            <form action="register.jsp" method="get">
                <button type="submit" class="btn">User Register</button>
            </form>
            <form action="sellerLogin.jsp" method="get">
                <button type="submit" class="btn">Seller Log In</button>
            </form>
            <form action="sellerRegister.jsp" method="get">
                <button type="submit" class="btn">Seller Register</button>
            </form>
        </div>
    </section>

    <section class="featured-products">
        <h3>Featured Products</h3>
        <div class="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Electronics.jpg" alt="Product 1">
                        <p>Electronics</p>
                    </div>
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Fashion.jpg" alt="Product 2">
                        <p>Fashion</p>
                    </div>
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Kitchen.jpg" alt="Product 3">
                        <p>Home & Kitchen</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Health.jpg" alt="Product 4">
                        <p>Beauty & Health</p>
                    </div>
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Books.jpg" alt="Product 5">
                        <p>Books & Stationery</p>
                    </div>
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Sports.jpg" alt="Product 6">
                        <p>Sports & Outdoors</p>
                    </div>
                    </div>
                    <div class="carousel-item">
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Baby.jpg" alt="Product 7">
                        <p>Toys & Baby Products</p>
                    </div>
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/Grocery.jpg" alt="Product 8">
                        <p>Grocery & Essentials</p>
                    </div>
                    </div>
                    
                
            </div>
            <button class="carousel-control prev" onclick="prevSlide()">&#10094;</button>
            <button class="carousel-control next" onclick="nextSlide()">&#10095;</button>
        </div>
    </section>

    <footer>
        <p>&copy; 2024 RevShop. All Rights Reserved.</p>
    </footer>
</body>
</html>
