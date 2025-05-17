<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="MODEL.SavedList" %>
<%@ page import="MODEL.ResponseCodeList" %>

<!DOCTYPE html>
<html>
<head>
    <title>View List - MOENGAGEAPP</title>
    <style>
        .dog-image {
            width: 100px;
            margin: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin-bottom: 15px;
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
    </style>
</head>
<body>
<%
    SavedList list = (SavedList) request.getAttribute("list");
    List<ResponseCodeList> items = (List<ResponseCodeList>) request.getAttribute("listItems");
    
    if (list == null) {
%> 
    <p class="error">List not found.</p>
<% } else { %>
    <h2>List: <%= list.getName() %></h2>
    <p>Created on: <%= list.getCreatedAt() != null ? list.getCreatedAt().toString() : "Unknown date" %></p>
    
    <% if (items != null && !items.isEmpty()) { %>
        <ul>
        <% for (ResponseCodeList code : items) { %>
            <li>
                <strong>HTTP <%= code.getCode() %>:</strong> <%= code.getDescription() %><br>
                <% if (code.getImageUrl() != null && !code.getImageUrl().isEmpty()) { %>
                    <img src="<%= code.getImageUrl() %>" 
                         alt="Dog representing HTTP <%= code.getCode() %>" 
                         class="dog-image"
                         onerror="this.onerror=null;this.src='images/default-dog.jpg';"/>
                <% } %>
            </li>
        <% } %>
        </ul>
    <% } else { %>
        <p>No items in this list.</p>
    <% } %>
<% } %>

<p><a href="lists.jsp">← Back to All Lists</a></p>
</body>
</html>