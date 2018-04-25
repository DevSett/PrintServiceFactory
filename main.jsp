<%--

  Created by IntelliJ IDEA.
  User: Libedinsky
  Date: 28.07.2017
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Главная</title>
    <link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
    <style>
        #myProgress {
            width: 100%;
            background-color: #ddd;
            display: none;
        }

        #myBar {
            color: black;
            width: 11%;
            height: 30px;
            background-color: #4CAF50;
            text-align: center;
            line-height: 30px;

        }
    </style>
</head>
<body>
<header class="w3-light-gray ">
    <table class="w3-table-all w3-padding-0 w3-light-gray">
        <tr>
            <th class="w3-light-gray">
                <a href="http://localhost/main" class="w3-button w3-blue-gray">${perm}</a>
            </th>
            <th class="w3-light-gray">
                <form action="<%=request.getContextPath()%>/login" method="get">
                    <input class="w3-button w3-red w3-right" value="Выйти" type="submit">
                </form>
            </th>
        </tr>
    </table>
</header>

