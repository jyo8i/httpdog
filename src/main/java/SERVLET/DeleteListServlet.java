package SERVLET;

import DAO.ListDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {
            int listId = Integer.parseInt(idParam);
            ListDAO dao = new ListDAO();
            dao.deleteList(listId);
        }
        response.sendRedirect("lists.jsp");
    }
}
