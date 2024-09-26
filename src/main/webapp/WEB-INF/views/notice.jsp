<%@ page import="com.example.hackingproject.notice.dto.NoticeReq" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항</title>
    <link rel="stylesheet" href="/css/notice_styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
</head>
<body>
<div class="notice-container">
    <div class="top">
        <div class="left"><i class="material-icons">arrow_back</i></div>
        <div class="center">루키증권</div>
        <div class="right"></div>
    </div>
    <header class="header">
        <h1>공지사항</h1>
    </header>

    <%
        List<NoticeReq> noticeList = (List<NoticeReq>) request.getAttribute("noticeList");

        for (NoticeReq notice : noticeList) {
            java.util.Date date = notice.getNOTICE_DATE();
            SimpleDateFormat sdf = new SimpleDateFormat("M월 d일", Locale.KOREA);
            String formattedDate = sdf.format(date);
    %>
    <div class="announcement">
        <a href="notice-detail?noticeNo=<%= notice.getNOTICE_NO() %>"><h2 class="NOTICE_TITLE"><%= notice.getNOTICE_TITLE() %></h2></a>
        <p class="NOTICE_DATE"><%= formattedDate %></p>
    </div>
    <% } %>


    <div class="submit-btn">
        <a href="notice-write" class="on">등록</a>
        <!--<a href="#">수정</a>-->
    </div>
</div>
</body>
</html>
