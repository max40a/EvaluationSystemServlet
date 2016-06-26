package configurations;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Retro on 15.06.2016.
 */
public class JdbcDriverLoadContextServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            Class.forName(ConfigurationJDBC.JDBC_DRIVER);
            System.out.println("JDBC DRIVER LOAD.");
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    ConfigurationJDBC.DB_ESS_URL,
                    ConfigurationJDBC.USER_NAME,
                    ConfigurationJDBC.USER_PASSWORD );
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("connection", connection);
        if(connection != null)
            System.out.println("Connection to ESS Got.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Application Shut Down.");
    }
}
