<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
    <style>
        table {
           width: 35%;
            border-spacing:0;
        }

        tr:nth-child(odd) {
            background: silver;
            }
        td{
            padding: 10px 2px;
        }
        .div1{
            margin: 20px 0px 5px 0px;
        }


    </style>
</head>
<body>
<h1>Моя еда</h1>
<h3><a href="index.html">Home</a></h3>
<table>
    <tr>
        <td width="30%"><b>Дата/Время</b></td>
        <td width="35%"><b>Описание</b></td>
        <td width="15%"><b>Калории</b></td>
        <td colspan="2" width="20%" align="center"><b>Операции</b></td>
    </tr>
    <c:set var="formatter" value="${formatter}"/>
    <c:forEach var="mealsWithExceed" items="${mealsWithExceed}">

        <tr style="${mealsWithExceed.isExceed()? 'color:red' : 'color:green'}">
            <td>${mealsWithExceed.getDateTime().format(formatter)}</td>
            <td>${mealsWithExceed.getDescription()} </td>
            <td>${mealsWithExceed.getCalories()} </td>
            <td><a href="meal?action=update&id=${mealsWithExceed.id}">Изменить</a></td>
            <td><a href="meal?action=delete&id=${mealsWithExceed.id}">Удалить</a></td>

        </tr>


    </c:forEach>
</table>
<div class="div1">
    <!-- Добавляем пищу-->
    <form method="get">

        <input hidden name="action" value="add"/>
        <input type="date" required="true" name="date"/>
        <input type="time" required="true" name="time"/>
        <input type="text" required="true" name="description" placeholder="Описание"/>
        <input type="number" min="1" required="true" name="calories" placeholder="Калории"/>
        <button type="submit">Добавить</button>


    </form>
</div>

</body>
</html>
