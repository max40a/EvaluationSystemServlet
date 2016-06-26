package admin;

import configurations.ConfigurationJDBC;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 25.06.2016.
 */
public class UpdateServlet extends HttpServlet {

    File updateForm = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\admin\\UpdateForm.html");

    String firstName = "";
    String lastName = "";
    String userName = "";
    String password = "";

    String html = "";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendUpdateForm(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        updateRecord(request, response);
    }

    private void sendUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        ServletContext context = getServletContext();
        String dbURL = (String) context.getAttribute("dbEssURL");

        String id = request.getParameter("id");

        try (Connection connection = DriverManager.getConnection(
                dbURL,
                ConfigurationJDBC.USER_NAME,
                ConfigurationJDBC.USER_PASSWORD
        )) {
            String sql = "SELECT firstname, lastname, username, password FROM users WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                firstName = resultSet.getString(1);
                lastName = resultSet.getString(2);
                userName = resultSet.getString(3);
                password = resultSet.getString(4);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        PrintWriter out = response.getWriter();

        html = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(updateForm))) {
            int i;
            while ((i = reader.read()) != -1) {
                html += (char) i;
            }

            html = html.replace("${firstName}", firstName);
            html = html.replace("${lastName}", lastName);
            html = html.replace("${userName}", userName);
            html = html.replace("${password}", password);
            html = html.replace("${resultMessage}", "");

            out.println(html);
        }
    }

    private void updateRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        ServletContext context = getServletContext();
        String dbUrl = (String) context.getAttribute("dbEssURL");

        String id = request.getParameter("id");
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        password = request.getParameter("password");

        try (Connection connection = DriverManager.getConnection(
                dbUrl,
                ConfigurationJDBC.USER_NAME,
                ConfigurationJDBC.USER_PASSWORD)) {
            String sql = "UPDATE users SET firstname=? , lastname=?, password=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, password);
            statement.setString(4, id);

            int j = statement.executeUpdate();
            try (BufferedReader reader = new BufferedReader(new FileReader(updateForm))) {
                html = "";
                String references = "<a href=/search_user>Go Back</a> To Search Page";

                int i;
                while ((i = reader.read()) != -1) {
                    html += (char) i;
                }

                if (j == 1) {
                    html = html.replace("${resultMessage}", "Record Updating<br>" + references);
                }
                else
                    html = html.replace("${resultMessage}", "Error Updating Record<br>" + references);

                html = html.replace("${firstName}", firstName);
                html = html.replace("${lastName}", lastName);
                html = html.replace("${userName}", userName);
                html = html.replace("${password}", password);
                html = html.replace("${resultMessage}", "");

                out.println(html);
            }

            statement.close();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}