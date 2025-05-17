<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Logged Out</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .message {
            background-color: #f8f8f8;
            border: 1px solid #ddd;
            padding: 20px;
            display: inline-block;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }
        a {
            display: inline-block;
            margin-top: 15px;
            text-decoration: none;
            color: #007BFF;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="message">
        <h2>You have been successfully logged out.</h2>
        <p><a href="login.jsp">Click here to log in again</a></p>
        <p><a href="index.jsp">Back to Home</a></p>
    </div>
</body>
</html>
