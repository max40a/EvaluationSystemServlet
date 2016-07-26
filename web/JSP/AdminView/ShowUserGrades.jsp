<%@ page import="javax.sql.rowset.CachedRowSet" %>
<%@ page import="java.sql.ResultSetMetaData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessinBean" scope="page" class="beans.SessionBean"/>
<jsp:useBean id="dbBean" scope="page" class="servlets.testsService.testBean.TestBean"/>
<%!
    HttpSession session = null;
    String welcomeURL = "/welcome";
%>
<%
    session = sessinBean.getSession(request, response);
    if (session != null) {
        if (!sessinBean.adminDetected(request))
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
        CachedRowSet cachedRowSet = dbBean.getUserGrades(userId);
        ResultSetMetaData metaData = cachedRowSet.getMetaData();

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
            while (cachedRowSet.next()) {
        %>
        <tr>
            <%
                for (int i = 1; i <= columnCount; i++) {
            %>
            <td><%=cachedRowSet.getString(i)%>
            </td>
            <%
                    }
                }
            %>
        </tr>
    </table>
</center>
</body>
</html>