<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <div>
        User: ${user}
    </div>
    <div>

        <form method="post">
            <input hidden name="action" value="filter"/>
            <table>
                <tr>
                    <td>From date:</td>
                    <td>To date:</td>
                    <td>From time:</td>
                    <td>To time:</td>
                </tr>
                <tr>
                    <td><input type="date" value="${param.fromDate}" name="fromDate"></td>
                    <td><input type="date" value="${param.toDate}" name="toDate"></td>
                    <td><input type="time" value="${param.fromTime}" name="fromTime"></td>
                    <td><input type="time" value="${param.toTime}" name="toTime"></td>
                </tr>
                <tr>

                    <td>
                        <button type="submit">Filter</button>
                    </td>

                </tr>
            </table>
        </form>
        <form action="meals">
            <button type="submit">Clear</button>
        </form>
    </div>

    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <p><b>${warning}</b></p>
</section>
</body>
</html>