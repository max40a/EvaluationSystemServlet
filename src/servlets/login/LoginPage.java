package servlets.login;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 15.06.2016.
 */
public class LoginPage extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendLoginForm(response, false);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if (login(userName, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("loginTrue", new String("true"));
            response.sendRedirect("/forum_category");
        } else {
            sendLoginForm(response, true);
        }
    }

    private void sendLoginForm(HttpServletResponse response, boolean withErrorMessage)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String html = "";
        String errorMessage = "Login Failed. Please try again.<br>";

        File loginForm = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\LoginPage.html");

        try (BufferedReader reader = new BufferedReader(new FileReader(loginForm))) {

            int i;
            while ((i = reader.read()) != -1) {
                html += (char) i;
            }

            if (withErrorMessage) {
                html = html.replace("${errorMessage}", errorMessage);
            } else {
                html = html.replace("${errorMessage}", "");
            }

            out.println(html);

        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public boolean login(String userName, String password) {

        ServletContext servletContext = getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("connection");

        try {
            String sql = "SELECT username FROM  users WHERE username=? AND password=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, userName);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                resultSet.close();
                statement.close();
                return true;
            }

            resultSet.close();
            statement.close();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return false;
    }
}
