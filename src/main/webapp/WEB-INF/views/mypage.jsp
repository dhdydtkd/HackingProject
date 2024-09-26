<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>루키증권-마이페이지</title>
    <link
      href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="/css/mypage_styles.css" />
  </head>

  <body>
  <!-- Scripts -->
  <script src="/js/jquery.min.js"></script>
  <script src="/js/browser.min.js"></script>
  <script src="/js/breakpoints.min.js"></script>
  <script src="/js/util.js"></script>
  <script src="/js/main.js"></script>
  <script src="/js/common.js"></script>
  <script src="/js/main_menu.js"></script>
    <div class="container">
      <div class="card">
        <div class="header">
          <div class="left"><i class="material-icons">arrow_back</i></div>
          <div class="center">마이페이지</div>
          <div class="right"></div>
        </div>

        <div class="account-info">
          <div class="account-number">루키증권 000-00-000000</div>
          <div class="balance">618,074원</div>
          <div class="button-group">
            <button class="button deposit">채우기</button>
            <button
              class="button transfer"
              onclick="window.location.href = 'transfer.html';"
            >
              보내기
            </button>
            <button class="button history">환전</button>
          </div>
        </div>

        <div class="grid-container">
          <span class="title">보유 주식</span>
          <div>
            <span class="value">613,978원</span>
            <span class="change positive">+10.7%</span>
          </div>

          <div>
            <button class="button detail">현황</button>
          </div>

          <span class="title">주문 가능 금액</span>
          <span class="value">4,096원</span>
          <div>
            <button class="button detail">확인</button>
          </div>

          <div>
            <span class="material-icons">trending_up</span>
            <span class="title">환율 · 미국달러</span>
          </div>
          <span class="value">7원</span>
          <div>
            <button class="button detail">구매내역</button>
          </div>

          <div>
            <span class="material-icons">attach_money</span>
            <span class="title">달러 · 환전</span>
          </div>
          <span class="value">$3.05</span>
          <div>
            <button class="button detail">구매내역</button>
          </div>
        </div>

        <div class="more-info">
          <span>더보기</span>
          <i class="material-icons">chevron_right</i>
        </div>
      </div>
    </div>
  </body>
</html>
