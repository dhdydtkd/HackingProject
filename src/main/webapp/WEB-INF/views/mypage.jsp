<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  <c:set var="myUserDataJson" value="${myUserDataJson}" />
  <script type="text/javascript">
    var myUserDataJsonString = '${myUserDataJson}';
    var myUserDataJson = JSON.parse(myUserDataJsonString.replaceAll('&quot;', '"'));

    $(document).ready(function(){
      userDataSetting(myUserDataJson);
      function updateUserData() {
        $.ajaxPOST("mypage/userinfo", null, function(result){
          if (result.state.code == "0000") {
            userDataSetting(result.body);
          }
        });
      }

      let intervalId = setInterval(updateUserData, 2000);
      window.addEventListener("blur", stopInterval);
      window.addEventListener("focus", startInterval);
      function stopInterval() {
        clearInterval(intervalId);
      }
      function startInterval() {
        intervalId = setInterval(updateUserData, 2000);
      }

      function userDataSetting(userData) {
        var percent = (userData.nowTotalPrice / userData.buyTotalPrice) * 100 -100;
        $('#account_balance').text(formattedString(userData.ACCOUNT_BALANCE)+"원");

        $('#now_total_price').text(formattedString(userData.nowTotalPrice)+"원");
        $('#buy_total_price').text(formattedString(userData.buyTotalPrice)+"원");
        $('#invest_pl_price').text(formattedString(userData.nowTotalPrice-userData.buyTotalPrice)+"원");

        $('#pl_percent').text(percent.toFixed(2)+"%");
        var pl_percent_element = document.getElementById("pl_percent");
        var invest_pl_price_element = document.getElementById("invest_pl_price");
        if(percent<0){
          pl_percent_element.classList.remove("change1");
          pl_percent_element.classList.add("change2");
          invest_pl_price_element.classList.remove("change1");
          invest_pl_price_element.classList.add("change2");
        }else{
          pl_percent_element.classList.remove("change2");
          pl_percent_element.classList.add("change1");
          invest_pl_price_element.classList.remove("change2");
          invest_pl_price_element.classList.add("change1");
        }
      }
        $('#back').click(function() {
            window.history.back();
        });
        $('#fill').click(function() {
            // window.location.href = 'transfer';
        });
        $('#send').click(function() {
            window.location.href = 'mypage/transfer';
        });
    });
  </script>


  <div class="container">
      <div class="card">
        <div class="header">
          <div id="back" class="left"><i class="material-icons">arrow_back</i></div>
          <div class="center">${user_nm}님의 마이페이지</div>
          <div class="right"></div>
        </div>

        <div class="account-info">
          <div class="account-number">${user_bank_name} ${user_account_number}</div>

          <div id="account_balance" class="balance">0원</div>
          <div class="button-group">
            <button id="fill" class="button deposit">
                충전
            </button>
            <button id="send" class="button transfer" onclick="">
              송금
            </button>
            <button class="button history">환전</button>
          </div>
        </div>

        <div class="grid-container">
          <span class="title">보유 주식</span>
          <div>
            <span id="now_total_price" class="value">0원</span>
            <span id="pl_percent" class="change1 positive">+10.7%</span>
          </div>

          <div>
            <button class="button detail">현황</button>
          </div>

          <span class="title">투자 금액</span>
          <span id="buy_total_price" class="value">0원</span>
          <div>
<%--            <button class="button detail">구매내역</button>--%>
          </div>

          <span class="title">투자 손익 금액</span>
          <span id="invest_pl_price" class="value">0원</span>
          <div>
<%--            <button class="button detail">확인</button>--%>
          </div>

          <div>
            <span class="material-icons">trending_up</span>
            <span class="title">환율 · 미국달러</span>
          </div>
          <span class="value">1$ = 1349.68</span>
          <div>
            <button class="button detail">구매내역</button>
          </div>

          <div>
            <span class="material-icons">attach_money</span>
            <span class="title">달러 · 환전</span>
          </div>
          <span class="value">3.05$</span>
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
