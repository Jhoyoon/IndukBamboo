<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<head>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
<nav id="nav">

</nav>
<script>
    const type = "${param.type}";
    if(type === "silver" || type === "english" || type === "secretary" || type === "tour" || type === "china" || type === "japan" || type === "money" ||
        type === "watching" || type === "multi" || type === "webtoon" || type === "vr"){
        $('#nav').append('<a href="/instorage/board/list?type=silver">은봉관</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=english">비지니스영어학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=secretary">비서학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=tour">관광서비스경영학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=china">비즈니스중국어과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=japan">비즈니스일본어과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=money">세무회계학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=watching">시각디자인과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=multi">멀티미디어디자인학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=webtoon">웹툰만화창작학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=vr">VR콘텐츠디자인학과</a><br/>');
    }else if(type === "auditorium"){
        $('#nav').append('<a href="/instorage/board/list?type=auditorium">강당</a><br/>');
    }else if(type === "virtue" || type === "architecture" || type === "wood" || type === "safety" || type === "inner"){
        $('#nav').append('<a href="/instorage/board/list?type=virtue">덕관</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=architecture">건축학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=wood">토목공학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=safety">건설안전공학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=inner">실내건축과</a><br/>');
    }else if(type === "person" || type === "industry" || type === "software" || type === "jewelry" || type === "ceramic" || type === "human"){
        $('#nav').append('<a href="/instorage/board/list?type=person">인관</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=industry">산업경영공학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=software">컴퓨터소프트웨어학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=jewelry">주얼리디자인학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=ceramic">리빙세라믹디자인학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=human">휴먼사회복지학과</a><br/>');
    }else if(type === "kinggod" || type === "kinggodgod" || type === "car"){
        $('#nav').append('<a href="/instorage/board/list?type=kinggod">제1공학관</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=kinggodgod">융합기계공학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=car">기계자동차학과</a><br/>');
    }else if(type === "library"){
        $('#nav').append('<a href="/instorage/board/list?type=library">도서관</a><br/>');
    }else if(type === "engineering" || type === "computer" || type === "mecha" || type === "broadcast" || type === "information" || type === "air"){
        $('#nav').append('<a href="/instorage/board/list?type=engineering">제2공학관</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=computer">컴퓨터전자공학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=mecha">메가트로닉스공학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=broadcast">방송영상미디어학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=information">정보통신공학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=air">글로벌항공서비스학과</a><br/>');
    }else if(type === "welfare"){
        $('#nav').append('<a href="/instorage/board/list?type=welfare">학생행복스퀘어</a><br/>');
    }else if(type === "formative" || type === "digital" || type === "influencer" || type === "city" || type === "beauty"){
        $('#nav').append('<a href="/instorage/board/list?type=formative">조형관</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=digital">디지털산업디자인학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=influencer">방송연예과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=city">도시디자인학과</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=beauty">방송뷰티과</a><br/>');
    }else if(type === "playground"){
        $('#nav').append('<a href="/instorage/board/list?type=playground">운동장</a><br/>');
    }else if(type === "yenji" || type === "gym" || type === "foodcourt" || type === "stationery" || type === "printer"){
        $('#nav').append('<a href="/instorage/board/list?type=yenji">연지스퀘어</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=gym">헬스장</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=foodcourt">식당</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=stationery ">문구점</a><br/>');
        $('#nav').append('<a href="/instorage/board/list?type=printer">프린터</a><br/>');
    }
</script>
