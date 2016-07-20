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
}