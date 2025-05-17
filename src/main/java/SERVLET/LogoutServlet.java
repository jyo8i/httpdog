package SERVLET;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate the current session
        HttpSession session = request.getSession(false); // avoid creating a new session
        if (session != null) {
            session.invalidate();
        }

        // Redirect to home page or login page
        response.sendRedirect("index.jsp");
    }
}
