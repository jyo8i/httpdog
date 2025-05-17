package SERVLET;

import DAO.UserDAO;
import MODEL.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/SignupServlet") 
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("username");
      
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(name);
       
        user.setPassword(password);

        UserDAO dao = new UserDAO();
        boolean success = dao.register(user);
        
        System.out.print(success);
        System.out.print("done");
        if (success) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("error", "Registration failed. Try again.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}
