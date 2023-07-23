<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
<header id="header">
    <div>
        <a href="<c:url value="/"/>">Instorage</a>
    </div>
    <c:choose>
        <c:when test="${sessionScope.id == null}">
            <div class="header_right-item">
                <form action="<c:url value="/login/login"/>" method="post">
                    <c:if test="${not empty param.error}">
                        <h1>${param.error}</h1>
                    </c:if>
                    <label for="id">id</label>
                    <input type="text" id="id" name="id" placeholder="id를 입력해주세요" value="${cookie.id.value}">

                    <label for="pwd">pwd</label>
                    <input type="password" id="pwd" name="pwd" placeholder="pwd를 입력해주세요">

                    <label for="rememberId">아이디 기억하기</label>
                    <input type="checkbox" id="rememberId" name="rememberId" ${empty cookie.id.value ? "" : "checked"}>
                    <button>전송</button>
                </form>
                <a href="<c:url value="/register"/>">회원가입하기</a>
            </div>
        </c:when>
        <c:when test="${sessionScope.id != null}">
         <div>
            <span>${sessionScope.id}</span>
            <a href="<c:url value="/"/>">마이페이지</a>
            <a href="<c:url value="/login/logout"/>">로그아웃</a>
         </div>
        </c:when>
    </c:choose>
</header>
