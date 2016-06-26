package servlets.admin;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String loginUrl = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect(loginUrl);
        } else {
            String loginTrue = (String) session.getAttribute("loginTrue");
            loginTrue = (loginTrue == null) ? "false" : loginTrue;
            if (!loginTrue.equals("true")) {
                response.sendRedirect(loginUrl);
            }
        }

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
        Connection connection = (Connection) context.getAttribute("connection");

        String id = request.getParameter("id");

        try {
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

            resultSet.close();
            statement.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        PrintWriter out = response.getWriter();

        try (BufferedReader reader = new BufferedReader(new FileReader(updateForm))) {
            String html = "";

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
        Connection connection = (Connection) context.getAttribute("connection");

        String id = request.getParameter("id");
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        password = request.getParameter("password");

        try {
            String sql = "UPDATE users SET firstname=? , lastname=?, password=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, password);
            statement.setString(4, id);

            int j = statement.executeUpdate();
            try (BufferedReader reader = new BufferedReader(new FileReader(updateForm))) {
                String html = "";
                String references = "<a href=/search_user>Go Back</a> To Search Page";

                int i;
                while ((i = reader.read()) != -1) {
                    html += (char) i;
                }

                if (j == 1) {
                    html = html.replace("${resultMessage}", "Record Updating<br>" + references);
                } else
                    html = html.replace("${resultMessage}", "Error Updating Record<br>" + references);

                html = html.replace("${firstName}", firstName);
                html = html.replace("${lastName}", lastName);
                html = html.replace("${userName}", userName);
                html = html.replace("${password}", password);

                out.println(html);
            }

            statement.close();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}