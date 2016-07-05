package servlets.login;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookie.getName().equals("userName"))
                userName = cookie.getValue();
        }

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        String admin = "";
        String adminLink = "";

        try {
            String sql = "SELECT admin_flag FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = resultSet.getString(1);
            }

            if(admin.equals("")) {
                admin = "0";
            }

            Integer adminFlag = Integer.parseInt(admin);

            if (adminFlag == 1) {
                adminLink =  "<a href=\"/search_user\">Search User</a>\n";
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(welcomePage))) {
            int i;
            while ((i = reader.read()) != -1) {
                html += (char) i;
            }

            html = html.replace("${userName}", userName);
            html = html.replace("${adminLink}", adminLink);
            out.println(html);
        }
    }
}
