package servlets.login;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 15.06.2016.
 */
public class LoginPage extends HttpServlet {

    File loginForm = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\LoginPage.html");

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

        if (login(userName, password, response)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("loginTrue", new String("true"));
            response.sendRedirect("/welcome");
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

    public boolean login(String userName, String password, HttpServletResponse response) {

        ServletContext servletContext = getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("connection");

        try {
            String sql = "SELECT username, id FROM  users WHERE username=? AND password=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, userName);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId;
                String userNameForCookie;

                userNameForCookie= resultSet.getString(1);
                userId = resultSet.getString(2);

                Cookie userIdCookie = new Cookie("userID", userId);
                response.addCookie(userIdCookie);

                Cookie userNameCookie = new Cookie("userName", userNameForCookie);
                response.addCookie(userNameCookie);

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
