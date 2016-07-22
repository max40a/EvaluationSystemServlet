package beans;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Retro on 22.07.2016.
 */
public class SessionBean {

    public static HttpSession getSession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginUrl = "/login_page";

        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("Session is failed.");
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

    public static boolean adminDetected(HttpServletRequest request)
            throws IOException {

        String userId = null;

        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];

            if (cookie.getName().equals("userID")) {
                userId = cookie.getValue();
            }
        }

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");

        int id = Integer.parseInt(userId);

        int adminState;
        try {
            String sql = "SELECT admin_flag FROM users WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                adminState = resultSet.getInt(1);
            else
                adminState = 0;

            if (adminState == 0)
                return false;

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return true;
    }
}