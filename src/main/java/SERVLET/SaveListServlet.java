package SERVLET;

import DAO.ListDAO;
import MODEL.ResponseCodeList;
import MODEL.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/SaveListServlet")
public class SaveListServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("user") == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    User user = (User) session.getAttribute("user");
	    String listName = request.getParameter("listName");

	    // Get searchResults from request attribute instead of session
	    @SuppressWarnings("unchecked")
	    ArrayList<ResponseCodeList> searchResults = (ArrayList<ResponseCodeList>) request.getAttribute("searchResults");
	    
	    // If not in request, try session as fallback
	    if (searchResults == null) {
	        searchResults = (ArrayList<ResponseCodeList>) session.getAttribute("searchResults");
	    }

	    if (searchResults == null || listName == null || listName.trim().isEmpty()) {
	        request.setAttribute("error", "Invalid list data");
	        request.getRequestDispatcher("search.jsp").forward(request, response);
	        return;
	    }

	    ListDAO dao = new ListDAO();
	    boolean success = dao.saveList(user.getId(), listName, new Date(), searchResults);

	    if (success) {
	        response.sendRedirect("lists.jsp");
	    } else {
	        request.setAttribute("error", "Failed to save list");
	        request.getRequestDispatcher("search.jsp").forward(request, response);
	    }}}
	