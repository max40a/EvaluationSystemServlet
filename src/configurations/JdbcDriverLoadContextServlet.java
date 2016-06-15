package configurations;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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

        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("dbEssURL", ConfigurationJDBC.DB_ESS_URL);

        System.out.println("Application initialized");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Application Shut Down.");
    }
}
