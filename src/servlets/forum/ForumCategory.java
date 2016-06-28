package servlets.forum;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 13.06.2016.
 */
public class ForumCategory extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginUrl = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("Session is Null");
            response.sendRedirect(loginUrl);
        } else {
            String loginTrue = (String) session.getAttribute("loginTrue");
            loginTrue = (loginTrue == null) ? "false" : loginTrue;//rboykock (c)
            if (!loginTrue.equals("true")) {
                System.out.println("Session is false");
                response.sendRedirect(loginUrl);
            }
        }

        Cookie[] cookies = request.getCookies();
        String userID = "";

        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if(cookie.getName().equals("userID"))
                userID = cookie.getValue();
        }

        Cookie userCookieId = new Cookie("userID" ,userID);
        response.addCookie(userCookieId);

        ServletContext servletContext = getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("connection");

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {
            String sql = "SELECT id, forum_category FROM forum_categories";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            File form = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\ForumCategory.html");
            try (BufferedReader reader = new BufferedReader(new FileReader(form))) {

                String html = "";
                String option = "";

                int i;
                while ((i = reader.read()) != -1) {
                    html += (char) i;
                }

                while (resultSet.next()) {
                    option += "<option value=" + resultSet.getString("id") + ">" + resultSet.getString("forum_category") + "</option><br>\n";
                }

                html = html.replace("${option}", option);

                out.println(html);

                resultSet.close();
                preparedStatement.close();

            } catch (FileNotFoundException exc) {
                exc.printStackTrace();
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        createForum(request);
        doGet(request, response);

    }

    void createForum(HttpServletRequest request)
            throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("connection");

        String forumName = request.getParameter("forumName");

        try {
            String sql = "INSERT INTO forum_categories VALUES (DEFAULT , ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, forumName);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                System.out.println("New Forum Create");
            }

            preparedStatement.getFetchDirection();
            preparedStatement.close();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}