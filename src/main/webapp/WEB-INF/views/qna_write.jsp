<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <!-- Scripts -->
    <script src="/js/jquery.min.js"></script>
    <script src="/js/browser.min.js"></script>
    <script src="/js/breakpoints.min.js"></script>
    <script src="/js/util.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/common.js"></script>
    <script src="/js/main_menu.js"></script>

    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Rookie 증권 - 문의사항 추가</title>
    <link
      href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="/css/qna_write_styles.css" />
  </head>
  <body>
  <script>
    $(document).ready(function(){
      $('#qna_add').click(function(){
        let data = {
          title: $("#title").val(),
          content: $("#content").val(),
        };

        $.ajaxPOST("qna/qnaadd", data, function(result){
          if (result.state.code == "0000") {
            alert("Q&A 등록이 완료되었습니다.\nQ&A 리스트화면으로 이동합니다.");
            window.location.href = "/qna";
          }else{
            alert("가입실패");
          }

        });
      });
      $('#back').click(function(){
        window.location.href = "/qna";
      });
    });
  </script>
  <header class="header">
    <div class="left">
      <i id="back" class="material-icons">arrow_back</i>
    </div>
    <div class="center">문의사항</div>
    <div class="right"></div>
  </header>
  <div class="context">루키증권 문의사항 글쓰기</div>

    <!-- 문의사항 추가 폼 -->
    <div class="form-container">
      <div class="form-group">
        <label for="title">제목:</label>
        <input type="text" id="title" name="QNA_TITLE" />
      </div>
      <div class="form-group">
        <label for="author">글쓴이:</label>
        <input type="text" id="author" name="USER_ID" value="${user_nm}(${user_id})" readonly/>
      </div>
      <div class="form-group">
        <label for="content">내용:</label>
        <textarea
          id="content"
          name="QNA_CONTEXT"
          rows="10"
        ></textarea>
      </div>
      <div class="form-group1">
        <button id="qna_add" type="button">문의글 올리기</button>
      </div>
    </div>
  </body>
</html>
