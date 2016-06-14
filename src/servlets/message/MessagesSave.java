package servlets.message;

import configurations.ConfigurationJDBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Retro on 12.06.2016.
 */
public class MessagesSave extends HttpServlet {

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

        String message = request.getParameter("message");
        Integer forumId = Integer.parseInt(request.getParameter("forumCategory"));

        try(Connection connection = DriverManager.getConnection(
                ConfigurationJDBC.DB_ESS_URL.getTitle(),
                ConfigurationJDBC.USER_NAME.getTitle(),
                ConfigurationJDBC.USER_PASSWORD.getTitle())) {

            String sql = "INSERT INTO messages VALUES (DEFAULT ,?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, forumId);

            int i = preparedStatement.executeUpdate();
            if(i != 0)
                System.out.println("Execute Update Successfully");

            preparedStatement.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/messages");
        requestDispatcher.forward(request, response);
    }
}
