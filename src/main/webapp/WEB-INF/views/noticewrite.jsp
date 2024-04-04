<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Rookie 증권 - 공지사항 추가</title>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="/css/notice-write_styles.css">
</head>
<body>
<div class="notice-header">
  <h1>공지사항</h1>
  <p>루키증권 공지사항 입니다.</p>
</div>

<!-- 공지사항 추가 폼 -->
<div class="form-container">
  <form action="/submit-notice" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="title">제목:</label>
      <input type="text" id="title" name="NOTICE_TITLE" required>
    </div>
    <div class="form-group">
      <label for="author">글쓴이:</label>
      <input type="text" id="author" name="USER_ID" required>
    </div>
    <div class="form-group">
      <label for="attachment">첨부파일:</label>
      <input type="file" id="attachment" name="NOTICE_FILE" onchange="return validateForm()">
    </div>
    <div class="form-group">
      <label for="content">내용:</label>
      <textarea id="content" name="NOTICE_CONTEXT" rows="10" required></textarea>
    </div>
    <div class="form-group">
      <button type="submit">공지사항 올리기</button>
    </div>
  </form>
</div>

<script>
  document.getElementById('attachment').addEventListener('change', function(event) {
    var fileInput = event.target;
    var filePath = fileInput.value;
    var fileName = filePath.split('\\').pop().split('/').pop(); // 파일 이름만 추출
    var fileExtensions = fileName.split('.'); // 모든 확장자 추출

    if (fileExtensions.length > 1) {
      var allowedExtensions = ["jpg", "jpeg", "png", "gif"];
      var isValid = true;

      // 확장자 비허용 목록 비교
      for (var i = 1; i < fileExtensions.length; i++) {
        if (!allowedExtensions.includes(fileExtensions[i].toLowerCase())) {
          isValid = false;
          break;
        }
      }

      // 확장자 제한 알림
      if (!isValid) {
        alert('올바른 파일 형식이 아닙니다.');
        fileInput.value = '';
      }
    }
  });
</script>
</body>
</html>
