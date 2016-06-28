package servlets.admin;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Retro on 27.06.2016.
 */
public class SearchMessagesUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String loginUrl = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("Session is Fault");
            response.sendRedirect(loginUrl);
        } else {
            String loginTrue = (String) session.getAttribute("loginTrue");
            loginTrue = (loginTrue == null) ? "false" : loginTrue;
            if (!loginTrue.equals("true"))
                response.sendRedirect(loginUrl);
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        ServletContext context = getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        String userId = request.getParameter("id");
        String foundMessage = "";

        try {
            String sql = "SELECT message FROM messages WHERE user_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foundMessage += "<li>" + resultSet.getString(1);
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        File searchMessagesPerUserForm = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\admin\\SearchMessagesUserForm.html");

        try (BufferedReader reader = new BufferedReader(new FileReader(searchMessagesPerUserForm))) {
            String html = "";

            int i;
            while ((i = reader.read()) != -1) {
                html += (char) i;
            }

            html = html.replace("${foundMessages}", foundMessage);

            out.println(html);
        }
    }

}
