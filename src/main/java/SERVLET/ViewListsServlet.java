package SERVLET;

import DAO.ListDAO;
import MODEL.SavedList;
import MODEL.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ViewListsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Enhanced debug logging
        System.out.println("\n===== ViewListsServlet START =====");
        System.out.println("DEBUG: Request received at " + new java.util.Date());
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("DEBUG: No active session or user not logged in");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        System.out.println("DEBUG: Authenticated user - ID: " + user.getId());
        
        try {
            ListDAO dao = new ListDAO();
            System.out.println("DEBUG: Attempting to retrieve lists for user " + user.getId());
            
            List<SavedList> savedLists = dao.getListsByUser(user.getId());
            
            // Enhanced debug output
            if (savedLists == null) {
                System.out.println("DEBUG: DAO returned null list");
            } else {
                System.out.println("DEBUG: Retrieved " + savedLists.size() + " lists");
                for (SavedList list : savedLists) {
                    System.out.println("DEBUG: List - ID: " + list.getId() + 
                                     ", Name: " + list.getName() + 
                                     ", Created: " + list.getCreatedAt());
                }
            }
            
            request.setAttribute("savedLists", savedLists);
            
            if (savedLists == null || savedLists.isEmpty()) {
                System.out.println("DEBUG: No lists found for user");
                request.setAttribute("message", "You don't have any saved lists yet.");
            }
        } catch (Exception e) {
            System.err.println("ERROR in ViewListsServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error loading your lists. Please try again.");
        } finally {
            System.out.println("===== ViewListsServlet END =====");
        }
        
        // Forward to JSP
        System.out.println("DEBUG: Forwarding to lists.jsp");
        request.getRequestDispatcher("/lists.jsp").forward(request, response);
    }
}