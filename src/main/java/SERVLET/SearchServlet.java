package SERVLET;

import MODEL.ResponseCodeList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filter = request.getParameter("query");  // ✅ Match 'name' in JSP input field
        ArrayList<ResponseCodeList> results = new ArrayList<>();

        if (filter != null && !filter.trim().isEmpty()) {
            String[] allCodes = {
                "100", "101", "102", "200", "201", "202", "203", "204", "205",
                "206", "207", "300", "301", "302", "303", "304", "305", "307",
                "308", "400", "401", "402", "403", "404", "405", "406", "407",
                "408", "409", "410", "411", "412", "413", "414", "415", "416",
                "417", "418", "421", "422", "423", "424", "426", "428", "429",
                "431", "451", "500", "501", "502", "503", "504", "505", "506",
                "507", "508", "510", "511"
            };

            String regex = filter.replace("x", "\\d");  // e.g., 2xx -> 2\d\d
            Pattern pattern = Pattern.compile("^" + regex + "$");

            for (String codeStr : allCodes) {
                if (pattern.matcher(codeStr).matches()) {
                    int code = Integer.parseInt(codeStr);
                    String description = "HTTP Status " + codeStr;
                    String imageUrl = "Image/" + codeStr + ".jpg";  // ✅ lowercase 'image'

                    results.add(new ResponseCodeList(code, description, imageUrl));
                }
            }
        }

        request.setAttribute("searchResults", results);
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // handle both GET and POST
    }
}
