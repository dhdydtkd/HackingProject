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
    <script>
        function downloadFile(noticeNo, fileName) {
            fetch(`/download?noticeNo=${noticeNo}&fileName=${fileName}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('파일을 다운로드하는 도중 오류가 발생했습니다.');
                    }
                    return response.blob();
                })
                .then(blob => {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = fileName;
                    document.body.appendChild(a);
                    a.click();
                    window.URL.revokeObjectURL(url);
                })
                .catch(error => {
                    console.error('에러:', error);
                    alert(error.message);
                });
        }
    </script>
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
        첨부파일 : <a href="javascript:void(0);" onclick="downloadFile(<%= noticeNo %>, '<%= noticeFileName %>')"><%= noticeFileName %></a>
        <% } %>
    </div>
    <div class="submit-btn">
        <!-- 공지사항 목록으로 이동 -->
        <a href="/main" class="on">목록으로</a>
    </div>
</div>
</body>
</html>
