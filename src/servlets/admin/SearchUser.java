package servlets.admin;

import beans.SessionBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 23.06.2016.
 */
public class SearchUser extends HttpServlet {

    File searchForm = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\admin\\SearchForm.html");

    String welcomeURL = "/welcome";
    HttpSession session;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        session = SessionBean.getSession(request, response);
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (session != null) {
            if (!SessionBean.adminDetected(request))
                response.sendRedirect(welcomeURL);
        }

        sendSearchResult(request, response);
    }

    public void sendSearchResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultSearch = "";

        String keyword = request.getParameter("keyword");

        ServletContext context = getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        PrintWriter out = response.getWriter();

        try {
            String sql = "SELECT id, firstname, lastname, username, password FROM users WHERE firstname LIKE ? OR lastname LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                resultSearch +=
                        "<tr class=\"row\">" + "<td>" + resultSet.getString(2) + "</td>"
                                + "<td>" + resultSet.getString(3) + "</td>"
                                + "<td>" + resultSet.getString(4) + "</td>"
                                + "<td>" + resultSet.getString(5) + "</td>"
                                + "<td>" + "<a href=/update?id=" + id + " class=\"button\">Update</a></td>"
                                + "<td>" + "<a href=/user_messages?id=" + id + " class=\"button\">View Messages</a>"
                                + "<td>" + "<a href=/JSP/AdminView/ShowUserGrades.jsp?userId=" + id + " class=\"button\">View Grades</a>" + "</tr>";
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
}