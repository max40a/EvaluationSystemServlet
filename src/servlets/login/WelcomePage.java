package servlets.login;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;

/**
 * Created by Retro on 30.06.2016.
 */
public class WelcomePage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginUrl = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("Session is Fail.");
            response.sendRedirect(loginUrl);
        } else {
            String loginTrue = (String) session.getAttribute("loginTrue");
            loginTrue = (loginTrue == null) ? "false" : loginTrue;//rboykock (c)
            if (!loginTrue.equals("true")) {
                System.out.println("Session is false");
                response.sendRedirect(loginUrl);
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        File welcomePage = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\WelcomePageForm.html");

        String html = "";

        Cookie[] cookies = request.getCookies();
        String userName = "";

        for (int i = 0; i <cookies.length ; i++) {
            Cookie cookie = cookies[i];
            if(cookie.getName().equals("userName"))
                userName = cookie.getValue();
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(welcomePage))) {
            int i;
            while ((i=reader.read()) != -1) {
                html += (char) i;
            }

            html = html.replace("${userName}", userName);
            out.println(html);
        }
    }
}
