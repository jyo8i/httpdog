<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
package MODEL;

<%@ page import="MODEL.SavedList" %>
<%@ page import="MODEL.ResponseCodeList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head><title>Edit List - MOENGAGEAPP</title></head>
<body>
<%
    SavedList list = (SavedList) request.getAttribute("list");
    List<ResponseCodeList> items = (List<ResponseCodeList>) request.getAttribute("listItems");
    if (list == null) {
%>
  <p>List not found.</p>
<% } else { %>
  <h2>Edit List: <%= list.getListName() %></h2>
  <form method="post" action="EditListServlet">
    <input type="hidden" name="listId" value="<%= list.getId() %>" />
    Rename List: <input type="text" name="listName" value="<%= list.getListName() %>" required /><br/><br/>

    <h3>Items in the list:</h3>
    <% if (items != null && !items.isEmpty()) { %>
      <ul>
      <% for (ResponseCodeList code : items) { %>
        <li>
          <%= code.getCode() %>: <%= code.getDescription() %>
          <a href="RemoveItemServlet?listId=<%= list.getId() %>&code=<%= code.getCode() %>">Remove</a>
        </li>
      <% } %>
      </ul>
    <% } else { %>
      <p>No items in this list.</p>
    <% } %>

    <input type="submit" value="Save Changes" />
  </form>
<% } %>
<p><a href="lists.jsp">Back to Lists</a></p>
</body>
</html>