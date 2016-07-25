package servlets.testsService.testBean;

import configurations.ConfigurationJDBC;

import java.sql.*;

/**
 * Created by Retro on 20.07.2016.
 */
public class TestBean {

    public int getTestId(String testName) {

        int testId = 0;

        try (Connection connection = DriverManager.getConnection(
                ConfigurationJDBC.DB_ESS_URL,
                ConfigurationJDBC.USER_NAME,
                ConfigurationJDBC.USER_PASSWORD)) {

            String sql = "SELECT id FROM tests WHERE testName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                testId = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return testId;
    }

    public static int gradeMake(int w, int a) {
        int grade;

        Double wrong = Double.valueOf(w);
        Double all = Double.valueOf(a);

        double rate = (wrong / all) * 100;
        System.out.println(rate);

        if (rate == 0.0) {
            grade = 5;
        } else if (rate == 100) {
            grade = 0;
        } else if (rate > 0.0 & rate <= 25.0) {
            grade = 4;
        } else if (rate > 25.0 & rate <= 50.0) {
            grade = 3;
        } else if (rate > 50.0 & rate <= 75.0) {
            grade = 2;
        } else {
            grade = 1;
        }

        return grade;
    }
}