package servlets.forum;

import configurations.ConfigurationJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

/**
 * Created by Retro on 13.06.2016.
 */
public class ForumCategory extends HttpServlet {

    public void init() {
        try {
            Class.forName(ConfigurationJDBC.JDBC_DRIVER.getTitle());
            System.out.println("JDBC ForumCategory Load.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(
                ConfigurationJDBC.DB_ESS_URL.getTitle(),
                ConfigurationJDBC.USER_NAME.getTitle(),
                ConfigurationJDBC.USER_PASSWORD.getTitle()
        )) {

            String sql = "SELECT id, forum_category FROM forum_categories";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            File form = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\ForumCategory.html");
            try (BufferedReader reader = new BufferedReader(new FileReader(form))) {

                String html = "";
                String option = "";

                int i;
                while ((i = reader.read()) != -1) {
                    html += (char) i;
                }

                while(resultSet.next()) {
                    option += "<option value="+resultSet.getString("id")+">"+resultSet.getString("forum_category")+"</option><br>\n";
                }

                html = html.replace("${option}", option);

                out.println(html);

            } catch (FileNotFoundException exc) {
                exc.printStackTrace();
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
