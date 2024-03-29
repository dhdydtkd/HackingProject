<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-03-29
  Time: 오후 2:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항</title>
    <link rel="stylesheet" href="/css/notice_detail.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
</head>
<body>
<div class="notice-container">
    <header class="header">
        <p id="NOTICE_TITLE">공지사항 제목</p>
    </header>
    <div class="noticeDetail">
        <p id="NOTICE_DATE">2024.03.29</p>
        <p id="NOTICE_CONTEXT">내용</p>
        <a href="다운로드 링크" id="NOTICE_FILE">첨부파일</a>
    </div>
    <div class="submit-btn">
        <!--공지사항 페이지로 가는 링크 넣어주기-->
        <a href="notice.html" class="on">목록으로</a>
    </div>
</div>
</body>
</html>