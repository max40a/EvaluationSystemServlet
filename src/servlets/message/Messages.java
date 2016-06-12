package servlets.message;

import configurations.ConfigurationJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
        sendMessageForm(response);


    }

    public void sendMessageForm(HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        File messageForm = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\static\\MessageForm.html");
        try(BufferedReader reader = new BufferedReader(new FileReader(messageForm))) {

            String html = "";

            int i;
            while ((i=reader.read()) != -1) {
                html += (char) i;
            }

            out.println(html);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
