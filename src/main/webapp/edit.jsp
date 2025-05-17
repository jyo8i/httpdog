<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="MODEL.SavedList" %>
<%
    SavedList list = (SavedList) request.getAttribute("list");
%>

<html>
<head>
    <title>Edit List</title>
</head>
<body>
    <h2>Edit List</h2>

    <form action="editList" method="post">
        <input type="hidden" name="id" value="<%= list.getId() %>" />
        <label>List Name:</label>
        <input type="text" name="name" value="<%= list.getName() %>" required />
        <button type="submit">Update</button>
    </form>

    <p><a href="viewLists">← Back to Lists</a></p>
</body>
</html>
