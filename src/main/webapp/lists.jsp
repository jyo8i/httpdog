<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="MODEL.SavedList" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
    <title>Your Saved Lists</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        .no-lists {
            padding: 20px;
            text-align: center;
            color: #666;
        }
    </style>
</head>
<body>
<%
    List<SavedList> savedLists = (List<SavedList>) request.getAttribute("savedLists");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
%>

<h2>Your Saved Lists</h2>

<% if (message != null) { %>
    <div class="alert success"><%= message %></div>
<% } %>
<% if (error != null) { %>
    <div class="alert error"><%= error %></div>
<% } %>

<% if (savedLists != null && !savedLists.isEmpty()) { %>
    <table>
        <thead>
            <tr>
                <th>List Name</th>
                <th>Created At</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% for (SavedList list : savedLists) { %>
            <tr>
                <td><%= list.getName() %></td>
                <td><%= dateFormat.format(list.getCreatedAt()) %></td>
                <td>
                    <a href="viewList?id=<%= list.getId() %>">View</a>
                    <a href="editList?id=<%= list.getId() %>">Edit</a>
                    <a href="deleteList?id=<%= list.getId() %>"
                       onclick="return confirm('Are you sure you want to delete this list?');">Delete</a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
<% } else { %>
    <div class="no-lists">
        <p>No saved lists found.</p>
        <p><a href="search.jsp">Create your first list</a></p>
    </div>
<% } %>
</body>
</html>