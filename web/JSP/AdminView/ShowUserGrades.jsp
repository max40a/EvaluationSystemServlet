<%@ page import="configurations.ConfigurationJDBC" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessinBean" scope="page" class="beans.SessionBean"/>
<%!
    HttpSession session = null;
    String welcomeURL = "/welcome";
%>
<%
    session = sessinBean.getSession(request, response);
    if(session != null) {
        if(!sessinBean.adminDetected(request))
            response.sendRedirect(welcomeURL);
    }

    String userIdS = request.getParameter("userId");
    if (userIdS == null) {
        userIdS = "-1";
    }
    int userId = Integer.parseInt(userIdS);
%>
<html>
<head>
    <title>Show User Grades</title>
    <style>
        body {
            background-color: #b1f5b9;
            font-family: Verdana;
        }
    </style>
</head>
<body>
<center>
<h1>Tests results:</h1>
<%
    Connection connection = DriverManager.getConnection(
            ConfigurationJDBC.DB_ESS_URL,
            ConfigurationJDBC.USER_NAME,
            ConfigurationJDBC.USER_PASSWORD);

    String sql = "SELECT users.username,tests.testName ,`test-grades`.grade, `test-grades`.`time-stamp`"
            + "FROM `test-grades` INNER JOIN users ON `test-grades`.userId = users.id "
            + "INNER JOIN tests ON `test-grades`.testId = tests.id "
            + "WHERE users.id = ?";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, userId);

    ResultSet resultSet = statement.executeQuery();

    ResultSetMetaData metaData = resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();
%>
<table border="1">
    <tr><%
        for (int i = 1; i <= columnCount; i++) {
    %>
        <td><b><%=metaData.getColumnName(i)%>
        </b></td>
        <%
            }
        %>
    </tr>
    <%
        while (resultSet.next()) {
    %>
    <tr>
        <%
            for (int i = 1; i <= columnCount; i++) {
        %>
        <td><%=resultSet.getString(i)%>
        </td>
        <%
                }
            }
        %>
    </tr>
</table>
<%
    resultSet.close();
    statement.close();
    connection.close();
%>
</center>
</body>
</html>