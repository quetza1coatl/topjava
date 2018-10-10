<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
           width: 40%;
            border-spacing:0;
        }

        tr:nth-child(odd) {
            background: silver;
            }
        td{
            padding: 10px 2px;
        }

    </style>
</head>
<body>
<h1>Моя еда</h1>
<h3><a href="index.html">Home</a></h3>
<table>
    <tr>
        <td width="40%"><b>Дата/Время</b></td>
        <td width="40%"><b>Описание</b></td>
        <td width="20%"><b>Калории</b></td>
    </tr>
    <c:set var="formatter" value="${formatter}"/>
    <c:forEach var="mealsWithExceed" items="${mealsWithExceed}">

        <tr style="${mealsWithExceed.isExceed()? 'color:red' : 'color:green'}">
            <td>${mealsWithExceed.getDateTime().format(formatter)}</td>
            <td>${mealsWithExceed.getDescription()} </td>
            <td>${mealsWithExceed.getCalories()} </td>

        </tr>


    </c:forEach>
</table>

</body>
</html>
