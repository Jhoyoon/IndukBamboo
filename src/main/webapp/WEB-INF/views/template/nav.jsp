<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<nav id="nav">
</nav>
<script>
    const mode ="${nav}";

    if(mode === "silver"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=silver"><i class="fa-solid fa-building-columns"></i>  은봉관</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=english"><i class="fa-solid fa-language"></i>  비지니스영어학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=secretary"><i class="fa-solid fa-user-tie"></i>  비서학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=tour"><i class="fa-solid fa-globe"></i>  관광서비스경영학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=china"><i class="fa-solid fa-language"></i>  비즈니스중국어과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=japan"><i class="fa-solid fa-language"></i>  비즈니스일본어과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=money"><i class="fa-solid fa-money-bill-1-wave"></i>  세무회계학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=watching"><i class="fa-solid fa-brush"></i>  시각디자인과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=multi"><i class="fa-solid fa-brush"></i>  멀티미디어디자인학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=webtoon"><i class="fa-solid fa-pen-nib"></i>  웹툰만화창작학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=vr"><i class="fa-solid fa-gamepad"></i>  게임&VR콘텐츠디자인학과</a></div>');
    }else if(mode === "auditorium"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=auditorium"><i class="fa-solid fa-church"></i>  교회</a></div>');
    }else if(mode === "virtue"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=virtue"><i class="fa-solid fa-building-columns"></i>  덕관</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=architecture"><i class="fa-solid fa-building"></i>  건축학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=wood"><i class="fa-solid fa-tree"></i>  토목공학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=safety"><i class="fa-solid fa-building-shield"></i>  건설안전공학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=inner"><i class="fa-solid fa-building"></i>  실내건축과</a></div>');
    }else if(mode === "person"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=person"><i class="fa-solid fa-building-columns"></i>  인관</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=industry"><i class="fa-solid fa-industry"></i>  산업경영공학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=software"><i class="fa-solid fa-computer"></i>  컴퓨터소프트웨어학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=jewelry"><i class="fa-regular fa-gem"></i>  주얼리디자인학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=ceramic"><i class="fa-solid fa-brush"></i>  리빙세라믹디자인학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=human"><i class="fa-solid fa-person"></i>  휴먼사회복지학과</a></div>');
    }else if(mode === "kinggod"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=kinggod"><i class="fa-solid fa-building-columns"></i>   제1공학관</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=kinggodgod"><i class="fa-solid fa-wrench"></i>  *융합기계공학과*</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=car"><i class="fa-solid fa-car"></i>  기계자동차학과</a></div>');
    }else if(mode === "library"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=library"><i class="fa-solid fa-book"></i>  도서관</a></div>');
    }else if(mode === "engineering"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=engineering"><i class="fa-solid fa-building-columns"></i>  제2공학관</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=computer"><i class="fa-solid fa-computer"></i>  컴퓨터전자공학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=mecha"><i class="fa-solid fa-robot"></i>  메가트로닉스공학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=broadcast"><i class="fa-solid fa-tower-broadcast"></i>  방송영상미디어학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=information"><i class="fa-solid fa-walkie-talkie"></i>  정보통신공학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=air"><i class="fa-solid fa-plane-departure"></i>  글로벌항공서비스학과</a></div>');
    }else if(mode === "welfare"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=welfare"><i class="fa-solid fa-building-columns"></i>  학생행복스퀘어</a></div>');
    }else if(mode === "formative"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=formative"><i class="fa-solid fa-building-columns"></i>  조형관</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=digital"><i class="fa-solid fa-brush"></i>  디지털산업디자인학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=influencer"><i class="fa-solid fa-podcast"></i>  방송연예과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=city"><i class="fa-solid fa-tree-city"></i>  도시디자인학과</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=beauty"><i class="fa-solid fa-brush"></i>  방송뷰티과</a></div>');
    }else if(mode === "playground"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=playground"><i class="fa-solid fa-futbol"></i>  운동장</a></div>');
    }else if(mode === "yenji"){
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=yenji"><i class="fa-solid fa-building-columns"></i>  연지스퀘어</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=gym"><i class="fa-solid fa-dumbbell"></i>  헬스장</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=foodcourt"><i class="fa-solid fa-utensils"></i>  식당</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=stationery "><i class="fa-solid fa-pencil"></i>  문구점</a></div>');
        $('#nav').append('<div class="nav_div"><a href="/instorage/board/list?type=printer"><i class="fa-solid fa-print"></i>  프린터</a></div>');
    }
</script>
