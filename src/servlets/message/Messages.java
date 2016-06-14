package servlets.message;

import configurations.ConfigurationJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 12.06.2016.
 */
public class Messages extends HttpServlet {

    @Override
    public void init() {
        try {
            Class.forName(ConfigurationJDBC.JDBC_DRIVER.getTitle());
            System.out.println("JDBC Message Load");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String forumId = request.getParameter("forumCategory");

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(
                ConfigurationJDBC.DB_ESS_URL.getTitle(),
                ConfigurationJDBC.USER_NAME.getTitle(),
                ConfigurationJDBC.USER_PASSWORD.getTitle())) {

            String sql = "SELECT message FROM messages";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            File file = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\MessageForm.html");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String message = "";
                while (resultSet.next()) {
                    message += "<li>"+resultSet.getString("message")+"</li><br>";
                }

                String html = "";

                int i;
                while ((i=reader.read()) != -1) {
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

}
