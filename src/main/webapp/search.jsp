<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>





<%@ page import="java.util.List" %>
<%@ page import="MODEL.ResponseCodeList" %>

<!DOCTYPE html>
<html>
<head>
    <title>Search HTTP Codes - MOENGAGEAPP</title>
</head>
<body>
<h2>Search HTTP Response Codes</h2>

<form method="get" action="SearchServlet">
    Search (e.g., 2xx, 404, 5x5): <input type="text" name="query" />
    <input type="submit" value="Search" />
</form>

<%
    // Get search results from request attribute set by SearchServlet
    List<ResponseCodeList> results = (List<ResponseCodeList>) request.getAttribute("searchResults");
    if (results != null && !results.isEmpty()) {
        // Store in session for later use if needed
        session.setAttribute("searchResults", results);
%>
    <h3>Results:</h3>
    <ul>
    <% for (ResponseCodeList code : results) { %>
        <li>
            <strong><%= code.getCode() %>:</strong> <%= code.getDescription() %><br/>
            <img src="<%= code.getImageUrl() %>" alt="Dog Image" width="100"
                 onerror="this.onerror=null;this.src='image/default.jpg';" /><br/>
        </li>
    <% } %>
    </ul>

    <form method="post" action="SaveListServlet">
        List Name: <input type="text" name="listName" required />
        <input type="submit" value="Save to List" />
    </form>
<% } else if (request.getParameter("query") != null) { %>
    <p>No results found.</p>
<% } %>

<p><a href="index.jsp">Back to Home</a></p>
</body>
</html>