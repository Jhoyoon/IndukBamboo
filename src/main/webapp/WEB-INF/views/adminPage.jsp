<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<html>
<head>
    <title>admin</title>
    <link rel="icon" type="image/png" sizes="32x32" href="../../resources/img/favicon.png">
</head>
<body>
<h1>회원가입한 유저 : <c:out value="${totalUserCount}"/></h1>
<h1>작성된 글 : <c:out value="${totalBoardCount}"/></h1>
</body>
</html>
