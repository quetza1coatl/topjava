<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>update...</title>

</head>
<body>
<h1>Изменить</h1>
<p>Текущие значения:</p>

<!-- Изменяем значения, текущие указаны по дефолту-->
<form method="post">

    <input hidden name="id" value="${meal.id}"/>
    Дата: <input type="date" required="true" name="date" value="${meal.getDate()}"/>
    Время: <input type="time" required="true" name="time" value="${meal.getTime()}"/>
    Описание:<input type="text" required="true" name="description" value="${meal.description}"/>
    Калории: <input type="number" min="1" required="true" name="calories" value="${meal.calories}"/>
    <button type="submit">Изменить</button>
    <button onclick="window.history.back()">Назад</button>

</form>
</div>



</body>
</html>
