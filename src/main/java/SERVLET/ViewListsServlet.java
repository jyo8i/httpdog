package SERVLET;

import DAO.ListDAO;
import MODEL.ResponseCodeList;
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

        String listIdParam = request.getParameter("id");
        ListDAO dao = new ListDAO();
        
        if (listIdParam != null && !listIdParam.isEmpty()) {
            try {
                int listId = Integer.parseInt(listIdParam);
                SavedList list = dao.getListById(listId);
                List<ResponseCodeList> items = dao.getItemsByListId(listId);

                request.setAttribute("list", list);
                request.setAttribute("listItems", items);
                request.getRequestDispatcher("viewList.jsp").forward(request, response);
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace(); // log and fallback to all lists
            }
        }
        
        
        try {
            ListDAO dao1 = new ListDAO();
            List<SavedList> savedLists = dao1.getListsByUser(user.getId());
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