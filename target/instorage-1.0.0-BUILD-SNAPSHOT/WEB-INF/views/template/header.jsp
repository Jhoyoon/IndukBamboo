<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<header id="header">
    <div id="header_left">
        <a href="<c:url value="/"/>"><i class="fa-regular fa-map"></i>인덕대학교 대나무숲</a>
    </div>
    <div id="header_right">
    <c:choose>
        <c:when test="${sessionScope.id == null}">
                <form action="<c:url value="/login/login"/>" method="post">
                    <input type="text" id="id" name="id" placeholder="아이디(id)" value="${cookie.id.value}">

                    <input type="password" id="pwd" name="pwd" placeholder="비밀번호(pwd)">

                    <label for="rememberId">아이디 기억하기</label>
                    <input type="checkbox" id="rememberId" name="rememberId" ${empty cookie.id.value ? "" : "checked"}>

                    <button class="button"><i class="fa-solid fa-right-to-bracket"></i> 로그인</button>
                </form>
                    <div id="header_right_a">
                        <a class="button" href="<c:url value="/register"/>"><i class="fa-solid fa-right-to-bracket"></i>  회원가입</a>
                    </div>



        </c:when>
        <c:when test="${sessionScope.id != null}">
            <div id="header_right_login">
                <span>${nickName}</span>
                <a class="button" href="<c:url value="/"/>">마이페이지</a>
                <a class="button" href="<c:url value="/login/logout"/>">로그아웃</a>
            </div>
        </c:when>
    </c:choose>
    </div>
</header>

