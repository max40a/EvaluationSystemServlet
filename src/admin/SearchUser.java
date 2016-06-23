package admin;

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
 * Created by Retro on 23.06.2016.
 */
public class SearchUser extends HttpServlet {

    File searchForm = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\admin\\SearchForm.html");

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String loginURL = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect(loginURL);
        } else {
            String loginTrue = (String) session.getAttribute("loginTrue");
            loginTrue = (loginTrue == null) ? "false" : loginTrue;
            if (!loginTrue.equals("true"))
                response.sendRedirect(loginURL);
        }

        sendSearchPage(response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendSearchResult(request, response);
    }

    public void sendSearchResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = "";
        String resultSearch = "";

        String keyword = request.getParameter("keyword");

        ServletContext context = getServletContext();
        String dbURL = (String) context.getAttribute("dbEssURL");

        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(
                dbURL,
                ConfigurationJDBC.USER_NAME,
                ConfigurationJDBC.USER_PASSWORD)) {

            String sql = "SELECT id, firstname, lastname, username, password FROM users WHERE firstname LIKE ? OR lastname LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getString(1);
                resultSearch +=
                        "<tr>" + "<td>" + resultSet.getString(2) + "</td>"
                                + "<td>" + resultSet.getString(3) + "</td>"
                                + "<td>" + resultSet.getString(4) + "</td>"
                                + "<td>" + resultSet.getString(5) + "</td>" + "</tr>";
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(searchForm))) {
                String html = "";
                int i;
                while ((i = reader.read()) != -1) {
                    html += (char) i;
                }

                html = html.replace("${resultSearch}", resultSearch);

                out.println(html);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    private void sendSearchPage(HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (BufferedReader reader = new BufferedReader(new FileReader(searchForm))) {
            String html = "";
            int i;
            while ((i = reader.read()) != -1) {
                html += (char) i;
            }
            html = html.replace("${resultSearch}", " ");

            out.println(html);
        }
    }
}