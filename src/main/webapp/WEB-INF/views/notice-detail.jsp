<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-03-29
  Time: 오후 2:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!-- NoticeReq 클래스 import -->

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
    <div class="top">
        <div class="left"></div>
        <div class="center">루키증권</div>
        <div class="right"></div>
    </div>
    <header class="header">
        <!-- 공지사항 제목 -->
        <p id="NOTICE_TITLE">${noticeTitle}</p>
    </header>
    <div class="noticeDetail">
        <!-- 공지사항 날짜 -->
        <%
            Date noticeDate = (Date) request.getAttribute("noticeDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            String formattedDate = dateFormat.format(noticeDate);
        %>
        <p id="NOTICE_DATE"><%= formattedDate %></p>
        <!-- 공지사항 내용 -->
        <p id="NOTICE_CONTEXT">${noticeContext}</p>
        <!-- 첨부 파일 다운 -->
        <%
            Integer noticeNo = (Integer) request.getAttribute("noticeNo");
            String noticeFileName = (String) request.getAttribute("noticeFileName");
            String noticeFilePath = (String) request.getAttribute("noticeFilePath");
            if (noticeFileName != null && !noticeFileName.isEmpty() && noticeFilePath != null && !noticeFilePath.isEmpty()) {
        %>
        첨부파일 : <a href="/download?noticeNo=<%= noticeNo %>&fileName=<%= noticeFileName %>"><%= noticeFileName %></a>
        <% } %>

    </div>
    <div class="submit-btn">
        <!-- 공지사항 목록으로 이동 -->
        <a href="/notice" class="on">목록으로</a>
    </div>
</div>
</body>
</html>
