package servlets.message;

import configurations.ConfigurationJDBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        String dbURL = (String) servletContext.getAttribute("dbEssURL");

        String message = request.getParameter("message");
        Integer forumId = Integer.parseInt(request.getParameter("forumCategory"));

        try(Connection connection = DriverManager.getConnection(
                dbURL,
                ConfigurationJDBC.USER_NAME,
                ConfigurationJDBC.USER_PASSWORD)) {

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
