package SERVLET;

import DAO.ListDAO;
import MODEL.SavedList;
import MODEL.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
@WebServlet("/viewLists")
public class ViewListsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            ListDAO dao = new ListDAO();
            List<SavedList> savedLists = dao.getListsByUser(user.getId());
            request.setAttribute("savedLists", savedLists);

            if (savedLists == null || savedLists.isEmpty()) {
                request.setAttribute("message", "You don't have any saved lists yet.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error loading your lists. Please try again.");
            e.printStackTrace();
        }

        request.getRequestDispatcher("/lists.jsp").forward(request, response);
    }
}