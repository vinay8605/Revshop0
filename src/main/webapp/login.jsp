<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
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
}

.background-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: linear-gradient(135deg, rgba(34, 34, 34, 0.9), rgba(45, 45, 45, 0.9));
    background-size: 400% 400%;
    animation: gradient 15s ease infinite, flicker 3s infinite alternate;
    z-index: -1; 
}

@keyframes gradient {
    0% { background-position: 0% 50%; }
    25% { background-position: 50% 50%; }
    50% { background-position: 100% 50%; }
    75% { background-position: 50% 50%; }
    100% { background-position: 0% 50%; }
}

@keyframes flicker {
    0% { background-color: #9b59b6; }
    10% { background-color: #3498db; }
    20% { background-color: #e67e22; }
    30% { background-color: #2ecc71; }
    40% { background-color: #f1c40f; }
    50% { background-color: #e74c3c; }
    60% { background-color: #1abc9c; }
    70% { background-color: #8e44ad; }
    100% { background-color: #9b59b6; }
}

form {
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 10px;
    padding: 30px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
    width: 90%;
    max-width: 400px;
    text-align: center;
}

h1 {
    margin-bottom: 20px;
    color: #333;
}

label {
    display: block;
    margin: 10px 0 5px;
    font-weight: bold;
    color: #555;
}

input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin-bottom: 20px;
    transition: border 0.3s;
}

input:focus {
    border-color: #6a11cb;
    outline: none;
}

button {
    width: 100%;
    padding: 10px;
    background-color: #6a11cb;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background 0.3s;
}

button:hover {
    background-color: #2575fc;
}

@media (max-width: 600px) {
    form {
        padding: 20px;
    }

    input, button {
        padding: 8px;
    }
}

</style>
</head>
<body>
<div class="background-overlay"></div>

    
    <form action="login" method="post">
    <h1>Login</h1>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Login</button>
    </form>

    
</body>
</html>
