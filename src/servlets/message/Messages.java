package servlets.message;

import configurations.ConfigurationJDBC;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 12.06.2016.
 */
public class Messages extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginUrl = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("Session is Null");
            response.sendRedirect(loginUrl);
        } else {
            String loginTrue = (String) session.getAttribute("loginTrue");
            loginTrue = (loginTrue == null) ? "false" : loginTrue;
            if (!loginTrue.equals("true"))
                response.sendRedirect(loginUrl);
        }

        ServletContext servletContext = getServletContext();
        String dbURL = (String) servletContext.getAttribute("dbEssURL");

        String forumId = request.getParameter("forumCategory");
        if (forumId == null) {
            forumId = "0";
        }
        Integer forumID = Integer.parseInt(forumId);

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(
                dbURL,
                ConfigurationJDBC.USER_NAME,
                ConfigurationJDBC.USER_PASSWORD)) {

            String sql = "SELECT message, time_stamp FROM messages WHERE forum_id=?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, forumID);

            ResultSet resultSet = ps.executeQuery();

            File file = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\MessageForm.html");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String message = "";
                while (resultSet.next()) {
                    message += "<li>" + resultSet.getString("message") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + resultSet.getString("time_stamp") + "</li><br>";
                }

                String html = "";

                int i;
                while ((i = reader.read()) != -1) {
                    html += (char) i;
                }

                html = html.replace("${forumId}", forumId);
                html = html.replace("${message}", message);

                out.println(html);

                resultSet.close();
                ps.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        String dbURL = (String) servletContext.getAttribute("dbEssURL");

        String message = request.getParameter("message");
        Integer forumId = Integer.parseInt(request.getParameter("forumCategory"));

        try (Connection connection = DriverManager.getConnection(
                dbURL,
                ConfigurationJDBC.USER_NAME,
                ConfigurationJDBC.USER_PASSWORD)) {

            String sql = "INSERT INTO messages VALUES (DEFAULT ,?, ? , DEFAULT)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, forumId);

            int i = preparedStatement.executeUpdate();
            if (i != 0)
                System.out.println("Execute Update Successfully");

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        doGet(request, response);
    }

}
