<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>문의게시판</title>
    <link rel="stylesheet" href="/css/qna_styles.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
      rel="stylesheet"
    />
  </head>
  <c:set var="addFlag" value="${add_flag}" />
  <script type="text/javascript">
    var qnaData = null;
    var addFlag = '${addFlag}';

    $(document).ready(function(){

      $.ajaxPOST("qna/myqna", null, function(result){
        if (result.state.code == "0000") {
          qnaData = result.body;
          if(addFlag=='true'){
            document.getElementById('qna_add').style.display = 'block';
          } else {
            document.getElementById('qna_add').style.display = 'none';
          }

          let contentBodyHTML = '';
          for(let i = 0 ;i<qnaData.length; i++){
            contentBodyHTML += "<div class='announcement'>";
            contentBodyHTML += "<div class='QNA-header'>";
            contentBodyHTML += "<h2 class='QNA_TITLE'>"+qnaData[i].Q_TITLE+"</h2>";
            if(qnaData[i].a_CONTENT != null) {
              contentBodyHTML += "<div class='answer'>답변완료</div>"
            }else{
              if(addFlag!='true') {
                contentBodyHTML += "<div class='button'>"
                // contentBodyHTML += "<a href='qna-write.html' class='on'>답변하기</a>"
                contentBodyHTML += "<a class='on'>답변하기</a>"
                contentBodyHTML += "</div>"
              }
            }
            contentBodyHTML += "</div>";
            contentBodyHTML += "<span class='QNA_CONTEXT'>"+qnaData[i].Q_CONTENT+"</span>";
            contentBodyHTML += "<div class='QNA-footer'>";
            contentBodyHTML += "<p class='QNA_USERID'>"+qnaData[i].USER_NAME+"("+qnaData[i].USER_ID+")"+"</p>";
            contentBodyHTML += "<p class='QNA_DATE'>"+qnaData[i].Q_CREATE_DT+"</p>";
            contentBodyHTML += "</div>";
            contentBodyHTML += "</div>";
            if(qnaData[i].a_CONTENT != null){
              contentBodyHTML += "<div class='response-indicator'>";
              contentBodyHTML += "<div class='response-content'>";
              contentBodyHTML += "<div class='response-footer'>";
              contentBodyHTML += "<p class='QNA_RESPONSE_DATE'>"+qnaData[i].A_CREATE_DT+"</p>";
              contentBodyHTML += "<span>"+qnaData[i].A_CONTENT+"</span>";
              contentBodyHTML += "</div>";
              contentBodyHTML += "</div>";
              contentBodyHTML += "</div>";
            }
          }

          $('#content').empty();
          $('#content').append(contentBodyHTML);
        }
      });

      $('#back').click(function(){
        window.location.href = "/main";
      });
    });
  </script>
  <body>
    <div class="qna-container">
      <header class="header">
        <div class="left">
          <i id="back" class="material-icons">arrow_back</i>
        </div>
        <div class="center">문의게시판</div>
        <div class="right"></div>
      </header>
      <section id="content">


      </section>
      <div style="display: none;" id="add_flag">${add_flag}</div>
      <div id="qna_add" class="submit-btn">
        <a href="/qnawrite" class="on">등록</a>
      </div>

    </div>
  </body>
</html>
