package SERVLET;

import DAO.ListDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/deleteList")
public class DeleteListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String listIdParam = request.getParameter("id");

        if (listIdParam != null) {
            try {
                int listId = Integer.parseInt(listIdParam);
                ListDAO dao = new ListDAO();
                dao.deleteListById(listId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("viewLists");
    }
}
