package SERVLET;

import DAO.ListDAO;
import MODEL.ResponseCodeList;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

public class EditListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int listId = Integer.parseInt(request.getParameter("listId"));
        String listName = request.getParameter("listName");

        @SuppressWarnings("unchecked")
        ArrayList<ResponseCodeList> updatedItems = (ArrayList<ResponseCodeList>) request.getSession().getAttribute("updatedList");

        if (updatedItems != null && listName != null) {
            ListDAO dao = new ListDAO();
            dao.updateList(listId, listName, updatedItems);
        }

        response.sendRedirect("lists.jsp");
    }
}
