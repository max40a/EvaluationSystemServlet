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
 * Created by Retro on 19.06.2016.
 */
public class RegistrationServlet extends HttpServlet {

    String message = "";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendRegistrationForm(response, false, message);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if (registration(firstName, lastName, userName, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loginTrue", "true");
            sendRegistrationForm(response, true, message);
        } else {
            sendRegistrationForm(response, false, message);
        }
    }

    private void sendRegistrationForm(HttpServletResponse response, boolean error, String message)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        File registrationPage = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\RegistrationPage.html");
        try (BufferedReader reader = new BufferedReader(new FileReader(registrationPage))) {
            String html = "";
            int i;
            while ((i = reader.read()) != -1) {
                html += (char) i;
            }
            if (!error) {
                String welcomeMessage = "";
                html = html.replace("${errorMessage}", message);
                html = html.replace("${welcomeMessage}", welcomeMessage);
                out.println(html);
            } else {
                String welcomeMessage = "Click this link to go to the program : <a href=/forum_category>Click</a>";
                html = html.replace("${errorMessage}", message);
                html = html.replace("${welcomeMessage}", welcomeMessage);
                out.println(html);
            }

        }

    }

    boolean registration(String firstName, String lastName, String userName, String password)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        try {
            String sqlInspect = "SELECT username FROM users WHERE username=?";
            PreparedStatement statementInspect = connection.prepareStatement(sqlInspect);
            statementInspect.setString(1, userName);

            ResultSet resultSet = statementInspect.executeQuery();
            if (resultSet.next()) {
                resultSet.close();
                statementInspect.close();
                message = "The user name <b>" + userName + "</b> has been taken. Please select another name.";
                return false;
            } else {
                String sqlInsert = "INSERT INTO users VALUES (DEFAULT , ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, userName);
                statement.setString(4, password);

                int i = statement.executeUpdate();
                if (i == 1) {
                    System.out.println("Successfully added one user");
                    message = "Congratulations, you have successfully registered.";
                }
                statement.close();
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return true;
    }
}