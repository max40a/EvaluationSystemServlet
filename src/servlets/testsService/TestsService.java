package servlets.testsService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by Retro on 10.07.2016.
 */
public class TestsService extends HttpServlet {

    private LinkedList<Integer> testsResult;
    private LinkedList<Integer> answerListDb = new LinkedList<>();

    Integer testId;

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

            boolean firstPage = Boolean.parseBoolean(request.getParameter("firstPage"));

            if(firstPage) {
                getAnswerListDB(request);
                testId = Integer.parseInt(request.getParameter("testId"));
                System.out.println(testId);
            }

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            saveAnswer(request);

            System.out.println("doPost::AnswerListDb: " + answerListDb.toString());
            System.out.println("doPost::TestsResult: " + testsResult.toString());

            if (testsResult.size() < answerListDb.size()) {
                String redirectAddress = request.getParameter("nextPage");
                response.sendRedirect(redirectAddress);
            } else if (testsResult.size() == answerListDb.size()) {
                ArrayList<Integer> wrongAnswers = searchWrongAnswers();
                Integer currentGrade = getGrade(wrongAnswers, request);
                compareAnswers(out, wrongAnswers, currentGrade);
                testsResult.clear();
            }
        }
    }

    private void getAnswerListDB(HttpServletRequest request)
            throws ServletException {
        Integer testId = Integer.parseInt(request.getParameter("testId"));

        ServletContext context = getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        try {
            String sql = "SELECT `test-answer` FROM `test-answers` WHERE testId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, testId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer answerDb = resultSet.getInt(1);
                answerListDb.add(answerDb);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    private void saveAnswer(HttpServletRequest request)
            throws ServletException, IOException {
        String name = request.getParameter("q");
        Integer valueInt = Integer.parseInt(name);
        testsResult.add(valueInt);
    }

    private void compareAnswers(PrintWriter out, ArrayList wrongAnswers, Integer grade) {
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
                html = html.replace("${grade}", grade.toString());
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
                html = html.replace("${grade}", grade.toString());
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

    private int getGrade(ArrayList<Integer> wrongAnswer, HttpServletRequest request)
            throws ServletException {
        int grade = 5 - wrongAnswer.size();

        saveGrades(grade, request);

        return grade;
    }

    private void saveGrades(Integer grade, HttpServletRequest request)
            throws ServletException {
        ServletContext context = getServletContext();
        Connection connection1 = (Connection) context.getAttribute("connection");

        Cookie[] cookie = request.getCookies();
        String userIdS = null;
        for (Cookie c : cookie) {
            if (c.getName().equals("userID"))
                userIdS = c.getValue();
        }
        Integer userId = Integer.parseInt(userIdS);
        System.out.println(testId);

        try {
            String sql = "INSERT INTO `test-grades` VALUES (DEFAULT, ?, ?, ?, DEFAULT)";
            PreparedStatement prepareStatement1 = connection1.prepareStatement(sql);

            prepareStatement1.setInt(1, userId);
            prepareStatement1.setInt(2, testId);
            prepareStatement1.setInt(3, grade);

            prepareStatement1.executeUpdate();
            prepareStatement1.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
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