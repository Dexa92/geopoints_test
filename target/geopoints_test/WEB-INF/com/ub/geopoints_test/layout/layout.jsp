<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Добро дожаловать!</title>
    <link rel="stylesheet" href="/static/client/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/static/client/css/custom.css"/>
</head>
<body>
<tiles:insertAttribute name="content"/>
</body>
</html>
