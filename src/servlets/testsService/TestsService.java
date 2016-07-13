package servlets.testsService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by Retro on 10.07.2016.
 */
public class TestsService extends HttpServlet {

    private LinkedList<Integer> testsResult;
    private LinkedList<Integer> answerListDb = new LinkedList<>();

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        try {
            String sql = "SELECT `test-answer` FROM `test-answers`";
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = searchSession(request, response);
        if (session != null) {
            response.sendRedirect("/welcome");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = searchSession(request, response);
        if (session != null) {

            testsResult = (LinkedList<Integer>) session.getAttribute("testsResult");

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            saveParameters(request);

            System.out.println("doPost::AnswerListDb: " + answerListDb.toString());
            System.out.println("doPost::TestsResult: " + testsResult.toString());

            if (testsResult.size() < answerListDb.size()) {
                String redirectAddress = request.getParameter("nextPage");
                response.sendRedirect(redirectAddress);
            } else if (testsResult.size() == answerListDb.size()) {
                ArrayList<Integer> wrongAnswers = searchWrongAnswers();
                compareAnswers(out, wrongAnswers);
                testsResult.clear();
            }
        }
    }

    private void saveParameters(HttpServletRequest request)
            throws ServletException, IOException {
        String name = request.getParameter("q");
        Integer valueInt = Integer.parseInt(name);
        testsResult.add(valueInt);
    }

    private void compareAnswers(PrintWriter out, ArrayList wrongAnswers) {
        File successFile = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\testsService\\static\\SuccessfulCompletion.html");
        File unsuccessFile = new File("C:\\Users\\Retro\\Desktop\\IDEA_project\\EvaluationSystemServlets\\src\\servlets\\testsService\\static\\Unsuccessful.html");
        String html = "";
        int i;
        try (BufferedReader reader = new BufferedReader(new FileReader(successFile));
             BufferedReader reader1 = new BufferedReader(new FileReader(unsuccessFile))) {
            if (testsResult.equals(answerListDb)) {
                while ((i = reader.read()) != -1) {
                    html += (char) i;
                }
                html = html.replace("${wrong}", "");
                out.println(html);
            } else {
                while ((i = reader1.read()) != -1) {
                    html += (char) i;
                }

                StringBuffer stringBuffer = new StringBuffer();
                for (int j = 0; j < wrongAnswers.size(); j++) {
                    if (j == wrongAnswers.size() - 1) {
                        stringBuffer.append(wrongAnswers.get(j));
                    } else {
                        stringBuffer.append(wrongAnswers.get(j) + ",");
                    }
                }

                html = html.replace("${wrong}", stringBuffer.toString());
                out.println(html);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private ArrayList searchWrongAnswers() {
        ArrayList<Integer> wrongAnswer = new ArrayList<>();
        for (int i = 0; i < answerListDb.size(); i++) {
            if (answerListDb.get(i) != testsResult.get(i))
                wrongAnswer.add(i + 1);
        }
        return wrongAnswer;
    }

    private HttpSession searchSession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String loginUrl = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("Session is Failed.");
            response.sendRedirect(loginUrl);
            return null;
        } else {
            String loginTrue = (String) session.getAttribute("loginTrue");
            loginTrue = (loginTrue == null) ? "false" : loginTrue;//rboykock (c)
            if (!loginTrue.equals("true")) {
                System.out.println("Session is false");
                response.sendRedirect(loginUrl);
                return null;
            }
        }
        return session;
    }
}