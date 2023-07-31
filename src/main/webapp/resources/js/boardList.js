$(document).ready(function() {
    if ($('#search_keyword').attr('autofocus')){
        let inputValue = $("#search_keyword").val();
        $("#search_keyword").focus().val("").val(inputValue);
    }
    includeError();
    removeError();
    // 서버로부터 받아온 값을 html-data를 통해서 받아온다
    let serverPageSize = $("#select_box").data("page-size");
    let serverPage = $("#select_box").data("page");
    let serverOption = $("#select_box").data("option");

    // 서버에서 보내준 pageSize값과 일치하는 option을 selected 해준다.
    $("#select_box option").each(function (){
        let option = $(this);
        if(Number(option.val()) === serverPageSize){
            option.attr('selected','selected');
        }
    });

    // 서버에서 보내준 option 값이 없을 경우 option을 'T'로 설정해준다.
    if (serverOption === undefined || serverOption === null || serverOption === '') {
        serverOption = 'T';
    }

    // keyword option
    // 서버에서 보내준 option값과 일치하는 option에 selected를 해준다.
    $("#option option").each(function (){
        let option = $(this);
        if(option.val() === serverOption)
            option.attr('selected','selected');
    });

    // 현재 페이지와 일치하는 페이지 번호의 색깔을 빨간색으로 바꿔준다.
    $("a").each(function() { // 모든 'a' 태그에 대해
        let pageLinkNumber = parseInt($(this).text(), 10); // 페이지 링크 번호를 정수로 가져오기
        if (pageLinkNumber === serverPage) { // 페이지 링크 번호가 현재 페이지 번호와 일치하면
            $(this).css("color", "red"); // 색상을 빨강으로 변경
        }
    });

    // pageSize select에 변화가 발생할시 form을 제출해준다.
    $("#select_box").change(function() {
        $(this).closest('form').submit(); // 폼 제출
    });

    // 게시물이 없을시 게시물 없음을 표시해준다.
    let none = $("#up").data("none");
    if(!(none.length === 0)){
        $("#up").after("<tr><td></td><td id='list_none'>"+none+"</td></tr>");
    }
});

// error 처리
let error = $("#error").data("error");
let includeError = function (){
    if(error !== undefined && error !== null && error !== ""){
        $("#error").css("display","flex");
    }
}
let removeError = function (){
    $("#error p:last-child").click(function(){
        $("#error").css("display", "none");
    });
}