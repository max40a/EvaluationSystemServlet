package servlets.testsService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

/**
 * Created by Retro on 10.07.2016.
 */
public class TestsService extends HttpServlet {

    LinkedList<Integer> testsResult = new LinkedList<>();
    LinkedList<Integer> answerListDb = new LinkedList<>();

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        try {
            String sql = "SELECT `test-answer` FROM `tests-answer`";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer answerDb = resultSet.getInt(1);
                answerListDb.add(answerDb);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        saveParameters(request, response);

        System.out.println("doPost::AnswerListDb: " + answerListDb.toString());
        System.out.println("doPost::TestsResult: " + testsResult.toString());

        if (testsResult.size() < answerListDb.size()) {
            String redirectAddress = request.getHeader("referer");
            response.sendRedirect(redirectAddress);
        } else if (testsResult.size() == answerListDb.size()) {
            compareAnswers(out);
            testsResult.clear();
        }

    }

    private void saveParameters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = request.getParameter(name);
            Integer valueInt = Integer.parseInt(value);
            testsResult.add(valueInt);
        }
    }

    private void compareAnswers(PrintWriter out) {
        String firstPage = "Tests1/Test1.jsp";

        if (testsResult.equals(answerListDb)) {
            out.println("<br><b>You gave the correct answer</b><br><a href=" + firstPage + ">Try Again</a>");
        } else {
            out.println("<br><b>You make a mistake in answer</b><br><a href=" + firstPage + ">Try Again</a>");
        }
    }
}