<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
<head>
  <title>글 읽기</title>
  <link rel="icon" type="image/png" sizes="32x32" href="../../resources/img/favicon.png">
  <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <script
          src="https://kit.fontawesome.com/634362e31b.js"
          crossorigin="anonymous"
  ></script>
</head>
<body data-bno="<c:out value='${board.bno}'/>" data-id="<c:out value='${sessionScope.id}'/>">
<jsp:include page="template/header.jsp" />
<div class="body_div" >
<jsp:include page="template/nav.jsp"/>
<article>
  <div class="title">
    <c:out value="${title}"/> 대나무숲
  </div>
  <div id="read_board">
    <div id="board_title_update_remove">
      <div id="board_title">
        <c:out value="${board.title}"/>
      </div>
      <div id="update_remove">
        <c:if test="${board.writer == sessionScope.id}">
        <div id="update_a_div">
          <a id="update_a" href="<c:url value="/board/edit?type=${param.type}&bno=${board.bno}&page=${page}&pageSize=${pageSize}"/>">수정</a>
        </div>
        <div id="remove_a_div">
          <a id="remove_a" href="<c:url value="/board/remove?type=${param.type}&bno=${board.bno}&page=${page}&pageSize=${pageSize}"/>">삭제</a>
        </div>
        </c:if>
      </div>
    </div>
    <div id="board_nickname_time_view_commentcnt">
      <div id="nickname_time">
        <div id="nickname"><c:out value="${board_nickname}"/></div>
        <div id="reg_date"><c:out value="${board.reg_date}"/></div>
      </div>
      <div id="view_commentcnt">
        <div id="view_cnt">조회수 <c:out value="${board.view_cnt}"/></div>
        <div id="comment_cnt">댓글  <c:out value="${board.comment_cnt}"/></div>
      </div>
    </div>
    <div id="board_content">
      <div style="white-space: pre;"><c:out value="${board.content}"/></div>
    </div>
    <h1 id = "comment_h1">전체 댓글 <span><c:out value="${board.comment_cnt}"/></span></h1>
    <div id="commentContainer"></div>
    <div id="replyBox" style="display: none">
      <div id="reply_div">
      <textarea id="replyInput" name="replyInput"></textarea>
      <button id="replySendBtn">저장</button>
      <button id="reply_cancel_btn">취소</button>
      </div>
      </div>
    <div id="comment_send">
      <textarea name="comment" id="comment"></textarea>
      <button id="sendBtn" type="button">SEND</button>
    </div>
  </div>
  <jsp:include page="template/footer.jsp" />
</article>
<div id="error" data-error="<c:out value="${error}"/>">
  <p><c:out value="${error}"/></p>
  <p>닫기</p>
</div>
  <script>
    let bno = $('body').data("bno");
    // ********************************************************
    // 페이지 방문시 댓글 생성 호출 함수
    $(document).ready(function (){
      showList(bno);
      includeError();
      removeError();
      $('#remove_a').click(function(event) {
        if(!confirm('정말로 삭제하시겠습니까?')) {
          event.preventDefault();
        }
      });
    })
    // ********************************************************************************************************************************
    // error 메세지 다루는 함수
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
    //*****************************************************************************************************************************
    // 모든 댓글을 만들어주는 함수.페이지 로드 시 자동으로 실행된다.
    let showList = function (bno){
      $.ajax({
        type:'get',       // 요청 메서드
        url: '/comments?bno='+bno,  // 요청 URI
        dataType : 'json', // 전송받을 데이터의 타입
        success : function(data){ // 콜백함수
          $("#commentContainer").html(doHtml(data));
        },
        error   : function(data){

          $("#error").css("display","flex");
          $("#error p:first-child").html(data.responseJSON[0].comment);
        }
      }); // $.ajax()
    }
    // *****************************************************************************************************************************
    // 댓글 작성

    $("#sendBtn").click(function (){
      // 이제 버튼 클릭시 write 이벤트를 작동시켜야 함
      write(bno);
    }); // sendBtn
    let write = function (){
      let comment = $("#comment").val();
      if(comment.trim() == ''){
        $("#error").css("display","flex");
        $("#error p:first-child").html("내용을 입력해 주세요!");
        return;
      }
      $.ajax({
        type:'post',       // 요청 메서드
        url: '/comments?bno='+bno,  // 요청 URI
        dataType : 'json', // 전송받을 데이터의 타입
        headers : { "content-type": "application/json"}, // 요청 헤더
        data : JSON.stringify({"comment":comment}),
        success : function(data){ // 콜백함수
          showList(bno);
          $("#comment").val('');
        },
        error   : function(data){
          if(data.responseJSON === undefined){
            $("#error p:first-child").html("너무 많은 요청을 보냈습니다.1분간 기다려주세요.");
            $("#error").css("display", "flex");
          }else{

            $("#error p:first-child").html(data.responseJSON.res);
            $("#error").css("display", "flex");

          }
        }
      }); // $.ajax()
    }
    // ************************************************************************************

    // 댓글 수정 기능
    $("#commentContainer").on("click",".updateBtn",function (){
      // 댓글 수정 버튼이 달려있는 해당 댓글의 li 요소를 매개변수로 넘겨준다.
      let liElement = $(this).closest("li");
      makeUpdate(liElement);
    })
    let makeUpdate = function (liElement){
      // 댓글 내용을 가져온다
      let commentText = liElement.find('span').html();
      commentText = commentText.replace(/<br>/g, "\n");
      // Check if updateInput and saveBtn already exists, if yes, remove them
      if(liElement.find('.updateInput').length > 0){
        liElement.find('.updateInput').remove();
        liElement.find('.saveBtn').remove();
      }
      let allDescendants = liElement.find("*");
      allDescendants.css("display","none");
      liElement.append('<div id="update_div"><textarea class="updateInput">'+commentText+'</textarea><button class="saveBtn">저장</button><button class="comment_cancel">취소</button></div>');
      // 여기서 해야할게...기존 댓글의 display 속성을 none으로 만드는것.그리고 취소 버튼도 만들고,거기서 클릭 이벤트를 거는것
    }
    $("#commentContainer").on("click",".saveBtn",function (event){
      event.preventDefault();
      let liElement = $(this).closest("li");

      let cno = liElement.attr("data-cno");

      let updatedComment = liElement.find('.updateInput').val();
      if(updatedComment.trim() == ''){
        $("#error").css("display","flex");
        $("#error p:first-child").html("내용을 입력해 주세요!");
        return;
      }
      update(cno, updatedComment);
    });
    let update = function(cno, comment){
      $.ajax({
        type:'patch',       // 요청 메서드
        url: '/comments/'+cno,  // 요청 URI
        dataType : 'json', // 전송받을 데이터의 타입
        headers : { "content-type": "application/json"}, // 요청 헤더
        data : JSON.stringify({"comment":comment}),
        success : function(data){ // 콜백함수
          showList(bno);
        },
        error   : function(data){
          $("#error").css("display","flex");
          $("#error p:first-child").html(data.responseJSON.res);
        } // 에러가 발생했을 때, 호출될 함수
      }); // $.ajax()
    }
    $("#commentContainer").on("click",".comment_cancel",function (event){
      event.preventDefault();
      let liElement = $(this).closest("li");

      liElement.find('*').each(function() {
        if ($(this).css('display') == 'none') {
          $(this).css('display', '');
        } else {
          $(this).css('display', 'none');
        }
      });
    });

    // ************************************************************************
    // 댓글 삭제 기능
    $("#commentContainer").on("click",".deleteBtn",function (){
      let cno = $(this).closest("li").attr("data-cno");
      let bno = $(this).closest("li").attr("data-bno");
      if (confirm("정말 삭제하시겠습니까?")){
        $.ajax({
          type:'delete',       // 요청 메서드
          url: '/comments/'+cno+'?bno='+bno,  // 요청 URI
          dataType : 'json', // 전송받을 데이터의 타입
          success : function(data){ // 콜백함수
            $("#error").css("display","flex");
            $("#error p:first-child").html(data.res);
            showList(bno);
          },
          error   : function(data){
            $("#error").css("display","flex");
            $("#error p:first-child").html(data.responseJSON.res); } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
      }

    })
    // *********************************************************************************************************
    // 답글을 만들어준다
    $("#commentContainer").on("click",".replyBtn",function (){
      // 보이게 만들고
      $("#replyBox").css("display","block");
      // 답글 버튼 부모의 자식으로 만들다.
      $("#replyBox").appendTo($(this).closest("li"));
    })
    $("#commentContainer").on("click","#reply_cancel_btn",function(){
      $("#replyBox").css("display","none");
      $("#replyInput").val('');
      $("#replyBox").appendTo("body");
    })

    // 답글 저장 로직
    $("#replySendBtn").click(function (){
      // 이제 버튼 클릭시 write 이벤트를 작동시켜야 함
      replyWrite();
    }); // sendBtn
    let replyWrite = function (){
      let comment = $("#replyInput").val();
      let cno=$("#replyBox").parent().attr("data-cno");
      if(comment.trim() == ''){
        $("#error").css("display","flex");
        $("#error p:first-child").html("내용을 입력해 주세요!");
        $("#replInput").focus();
        return;
      }
      $.ajax({
        type:'post',       // 요청 메서드
        url: '/comments?bno='+bno,  // 요청 URI
        dataType : 'json', // 전송받을 데이터의 타입
        headers : { "content-type": "application/json"}, // 요청 헤더
        data : JSON.stringify({"pcno":cno,"comment":comment}),
        success : function(data){ // 콜백함수
          showList(bno);
        },
        error   : function(data) {
          if (data.responseJSON === undefined) {
            $("#error p:first-child").html("너무 많은 요청을 보냈습니다.1분간 기다려주세요.");
            $("#error").css("display", "flex");
          } else {
            $("#error p:first-child").html(data.responseJSON.res);
            $("#error").css("display", "flex");

          }
        }
      }); // $.ajax()
      $("#replyBox").css("display","none");
      $("#replyInput").val('');
      $("#replyBox").appendTo("body");
    }
    // ****************************************************************************
    function dateFormatter(reg){
      let date = new Date(reg);

      let year = date.getFullYear();
      let month = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
      let day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();

      let hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

      let formattedDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
      return formattedDate;
    }

    let doHtml = function (comments){
      let tmp = "<ul>";
      let id = $("body").data("id");
      comments.forEach(function (comments){

        let date = dateFormatter(comments.reg_date);
        tmp = tmp + "<li data-cno='"+comments.cno+"'"
        tmp = tmp + " data-bno='"+comments.bno+"'"
        tmp = tmp + " data-pcno='"+comments.pcno+"'"
        if(comments.cno != comments.pcno){
          tmp = tmp + "class='comment_liR' >"
        }else{
          tmp = tmp + "class='comment_li' >"
        }
        if(comments.deleted == 1){
          tmp = tmp +"<div class='comment_li_commenter'>"
          tmp = tmp +"<div class='comment_li_commenter_commenter'>"
          tmp = tmp + ""+comments.nickname
          tmp = tmp + "</div>"
          tmp = tmp + "<div class = 'comment_li_commenter_update'>"
          if(id === comments.commenter){
            tmp = tmp + "<button class='updateBtn'>수정</button>"
            tmp = tmp + "<button class='deleteBtn'>삭제</button>"
          }
          tmp = tmp + "</div>"
          tmp = tmp + "</div>"
          tmp = tmp + "<div class='comment_li_comment'>"
          tmp = tmp +"<span style='white-space: pre-line;'>"+comments.comment+"</span>"
          tmp = tmp + "</div>"
          tmp = tmp + "<div class='comment_li_reply'>"
          if(comments.cno == comments.pcno)
            tmp = tmp + "<button class='replyBtn'>답글</button>"
          else{
            tmp = tmp + "<div id='tmp'></div>"
          }
          tmp = tmp + "<div id='comment_li_reg_date'>"+date+"</div>"
          tmp = tmp + "</div>"
          tmp = tmp + "</li>"
        }else{
          tmp = tmp +"<div id='deleted_comment'>삭제된 댓글 입니다.</div>"
        }

      })
      return tmp+"</ul>";
    }

  </script>
</body>
</html>
