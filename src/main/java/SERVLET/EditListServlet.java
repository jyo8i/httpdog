package SERVLET;

import DAO.ListDAO;
import MODEL.SavedList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/editList")
public class EditListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                ListDAO dao = new ListDAO();
                SavedList list = dao.getListById(id);

                request.setAttribute("list", list);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error loading list.");
            }
        }

        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String newName = request.getParameter("name");

        try {
            int id = Integer.parseInt(idParam);

            ListDAO dao = new ListDAO();
            dao.updateListName(id, newName);

            response.sendRedirect("viewLists");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error updating list.");
            doGet(request, response);
        }
    }
}
