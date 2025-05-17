<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="MODEL.SavedList" %>
<%@ page import="MODEL.ResponseCodeList" %>

<!DOCTYPE html>
<html>
<head>
    <title>View List - MOENGAGEAPP</title>
    <style>
        .dog-image { width: 100px; border: 1px solid #ccc; border-radius: 5px; }
        ul { list-style-type: none; padding: 0; }
        li { margin-bottom: 10px; }
    </style>
</head>
<body>
<%
    SavedList list = (SavedList) request.getAttribute("list");
    List<ResponseCodeList> items = (List<ResponseCodeList>) request.getAttribute("listItems");
%>

<% if (list != null) { %>
    <h2>List: <%= list.getName() %></h2>
    <p>Created on: <%= list.getCreatedAt() %></p>

    <% if (items != null && !items.isEmpty()) { %>
        <ul>
            <% for (ResponseCodeList code : items) { %>
                <li>
                    <strong><%= code.getCode() %>:</strong> <%= code.getDescription() %><br>
                    <img src="<%= code.getImageUrl() %>" class="dog-image"
                         alt="Dog for <%= code.getCode() %>" onerror="this.src='images/default.jpg';"/>
                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No items in this list.</p>
    <% } %>
<% } else { %>
    <p>List not found.</p>
<% } %>

<p><a href="lists.jsp">← Back to All Lists</a></p>
</body>
</html>
