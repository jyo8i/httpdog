<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head><title>Sign Up - MOENGAGEAPP</title></head>
<body>
<h2>Sign Up</h2>
<form method="post" action="SignupServlet">
  Username: <input type="text" name="username" required /><br/>
 
  Password: <input type="password" name="password" required /><br/>
  Confirm Password: <input type="password" name="confirm_password" required /><br/>
  <input type="submit" value="Sign Up" />
</form>
<p>Already have an account? <a href="login.jsp">Login here</a></p>
</body>
</html>
