package servlets.testsService.testBean;

import configurations.ConfigurationJDBC;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

/**
 * Created by Retro on 20.07.2016.
 */
public class TestBean {

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    ConfigurationJDBC.DB_ESS_URL,
                    ConfigurationJDBC.USER_NAME,
                    ConfigurationJDBC.USER_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public int getTestId(String testName)
            throws SQLException {

        int testId = 0;

        Connection connection = getConnection();

        String sql = "SELECT id FROM tests WHERE testName = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, testName);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            testId = resultSet.getInt(1);

        return testId;
    }

    public CachedRowSet getUserGrades(int userId) throws SQLException {

        Connection connection = getConnection();

        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet cachedRowSet = factory.createCachedRowSet();

        String sql = "SELECT u.username, t.testName ,tg.grade,  tg.`time-stamp` " +
                "FROM `test-grades`tg INNER JOIN users u " +
                "ON  tg.userid = u.id " +
                "INNER JOIN tests t " +
                "ON tg.testid = t.id " +
                "WHERE u.id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        ResultSet resultSet = statement.executeQuery();
        cachedRowSet.populate(resultSet);

        connection.close();

        return cachedRowSet;
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