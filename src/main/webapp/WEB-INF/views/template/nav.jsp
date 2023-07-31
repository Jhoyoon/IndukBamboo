<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<nav id="nav" data-nav="<c:out value="${nav}"/>">
</nav>
<!-- c url param 태그를 사용하면 한글을 바로 주고받을수 있다.하지만 결국 서버에서 오는 값에 따라서 다른 nav를 보여줘야 하니까 또같은듯?-->
<script src="<c:url value="/resources/js/nav.js"/>">
</script>
