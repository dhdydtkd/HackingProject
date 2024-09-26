<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="css/transfer.css">
<script src="js/transfer.js"></script>
</head>
<body>

  <div class="container">
    <div class="card">
      <div class="header">
        <div class="left"><i class="material-icons">arrow_back</i></div>
        <div class="center">송금</div>
        <div class="right"></div>
      </div>
      <div class="form-box">
        <form action="transferForm" method="post">
          <div class="form-row">
            <select id="TRANSFER_BANKAGENCY">
              <option value="" selected disabled>은행 선택</option>
              <option value="option1">카카오뱅크</option>
              <option value="option2">신한은행</option>
              <option value="option3">Sh수협은행</option>
              <option value="option4">KB국민은행</option>
              <option value="option5">NH농협은행</option>
              <option value="option6">RK루키은행</option>
            </select>
          </div>
          <div class="form-row">
            <input type="text" id="TRANSFER_NAME" placeholder="받는 분">
          </div>
          <div class="form-row">
            <input type="text" id="TRANSFER_DEPO" placeholder="계좌번호 입력">
          </div>
          <div class="form-row">
            <input type="text" id="TRANSFER_AMOUNT" placeholder="이체 금액">
          </div>
          <div class="form-row">
            <input type="submit" value="확인">
          </div>
        </form>
      </div>

    </div>
  </div>


</body>
</html>
