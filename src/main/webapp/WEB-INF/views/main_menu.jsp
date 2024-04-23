<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.hackingproject.notice.dto.NoticeReq" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>루키증권</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/main_menu.css">
    <script src="https://cdn.tailwindcss.com"></script>
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
<c:set var="stockListJson" value="${stockListJson}" />
<script>
$(document).ready(function(){
	$('#search-button').click(function(){
	    var keyword = $('#search-input').val();
	    $.ajax({
	        url: '/search', // 요청을 보낼 경로
	        type: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify({
	            keywords: keyword // stockCode 변수의 값을 사용
	        }),
	        success: function(stockResponse) {
                if(stockResponse.MSG==true){
                    let stockBodyHTML = "";
                    for(var i = 0; i<stockResponse.stockList.length; i++){
                        var stock_code = stockResponse.stockList[i]["STOCK_CODE"];
                        var stock_name = stockResponse.stockList[i]["STOCK_NAME"];
                        var stock_price = stockResponse.stockList[i]["STOCK_PRICE"];

                        var detailstockdata = "/detailstock?stockCode="+stock_code+"&stockName="+stock_name;
                        stockBodyHTML += "<a href="+detailstockdata+">";
                        stockBodyHTML += "<li>";
                        stockBodyHTML += "<span class='stock-number'>"+(i+1)+"</span>";
                        stockBodyHTML += "<p name='STOCK_NAME'>"+stock_name+"</p>";
                        stockBodyHTML += "<p name='STOCK_PRICE'>"+formattedString(stock_price)+"원</p>";

                        stockBodyHTML += "</li>";
                        stockBodyHTML += "</a>";

                    }

                    $('#search_result').empty();
                    $('#search_result').append(stockBodyHTML);
                    $('.search-result-section').show();

                }
                else{
                    $('.search-result-section').hide();
                }

	        },
	        error: function(xhr, status, error) {
	            alert('Stock 정보 로드 실패: ' + error);
	        }
	    });
	});
});
</script>
<script>

    var stockListJsonString = '${stockListJson}';
    var stockListJson = JSON.parse(stockListJsonString.replaceAll('&quot;', '"'));

    $(() => {
        stockListSetting(stockListJson);
        getStockIndex();
        let intervalId = setInterval(StockListSearch, 2000); // 1000 밀리초 = 1초
        let intervalId2 = setInterval(getStockIndex, 10000);
        window.addEventListener("blur", stopInterval);
        window.addEventListener("focus", startInterval);
        function stopInterval() {
            clearInterval(intervalId);
            clearInterval(intervalId2);
        }
        function startInterval() {
            intervalId = setInterval(StockListSearch, 2000);
            intervalId2 = setInterval(StockListSearch, 10000);
        }


        var login_flag = $('#login_flag').text();
        if(login_flag=='false'){
            $('#recently_viewed_stocks').hide();
            $('#login').show();
            $('#logout').hide();
        }else{
            $('#login').hide();
            $('#logout').show();
        }
        $("#logout").click(function() {
            //세션 만료  삭제
            $.ajaxPOST("login/logout", null, function(result){
                if (result.state.code == "0000") {
                    alert("로그아웃 되었습니다.");
                    location.reload();
                }
            });
        });

    });

    function StockListSearch(){
        $.ajaxGET("stock/stocklist", null, function(result){
            if (result.state.code == "0000") {
                let stockListData = result.body;
                stockListSetting(stockListData);
            }
        });
    }
    function stockListSetting(stockListData) {
        let stockBodyHTML = "";
        var stockBaseList = []
        function addStock(name, price) {
            var stock = {
                name: name,
                price: price
            };
            stockBaseList.push(stock);
        }
        addStock("AAPL", 100);
        addStock("AMZN", 1000);
        addStock("FB", 1500);
        addStock("GOOGL", 22000);
        addStock("MSFT", 100000);

        function calculatePercentage(base, value) {
            return (value / base) * 100;
        }

        for(let i = 0 ; i<stockListData.length ; i++){
            var stockbasePrice = 0;
            for(let j = 0 ;j<stockBaseList.length; j++){
                if(stockBaseList[j].name == stockListData[i].STOCK_CODE){
                    stockbasePrice = stockBaseList[j].price;
                    break;
                }
            }
            var percentage = calculatePercentage(stockbasePrice,stockListData[i].STOCK_PRICE) -100;
            var detailstockdata = "/detailstock?stockCode="+stockListData[i].STOCK_CODE+"&stockName="+stockListData[i].STOCK_NAME;
            stockBodyHTML += "<a href="+detailstockdata+">";
            stockBodyHTML += "<li>";
            stockBodyHTML += "<span class='stock-number'>"+(i+1)+"</span>";
            stockBodyHTML += "<p name='STOCK_NAME'>"+stockListData[i].STOCK_NAME+"</p>";
            stockBodyHTML += "<p name='STOCK_PRICE'>"+formattedString(stockListData[i].STOCK_PRICE)+"원</p>";
            //stockBodyHTML += "<p name='STOCK_PRICE'> 500원</p>";

            if(percentage<0){
                stockBodyHTML += "<p class='rate negative' name='STOCK_RATE'>"+percentage.toFixed(2)+"%</p>";
            }else{
                stockBodyHTML += "<p class='rate positive' name='STOCK_RATE'>+"+percentage.toFixed(2)+"%</p>";
            }
            stockBodyHTML += "</li>";
            stockBodyHTML += "</a>";
        }

        $('#stock_list').empty();
        $('#stock_list').append(stockBodyHTML);
    }
    function getStockIndex(){
        index_string = ["kospi","kosdaq"]
        $.ajaxGET("stock/stockindex", null, function(result){
            if (result.state.code == "0000") {
                let stockIndexList = result.body;
                for(let i=0;i<stockIndexList.length;i++){

                    var index_name = index_string[i];
                    var index_updown = stockIndexList[i].stock_INDEX_UPDOWN;

                    var index_num = stockIndexList[i].stock_INDEX_NUM;

                    if(index_updown == "상승"){
                        var index_change = "+" + stockIndexList[i].stock_INDEX_CNG;
                    } else{
                        var index_change = stockIndexList[i].stock_INDEX_CNG;
                    }

                    if(index_name != "exchange_rate"){
                        index_change += " (" + stockIndexList[i].stock_INDEX_CNG_PERCENT+")";
                    }

                    var Element = document.getElementById(index_string[i]);
                    Element.querySelector(".index-value").innerText=index_num;
                    Element.querySelector(".index-change").innerText=index_change;

                    if(index_updown=="상승"){
                        Element.querySelector(".index-change").classList.remove("negative");
                        Element.querySelector(".index-change").classList.add("positive");
                    }
                }
            }
        });
    }
</script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>루키증권</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>

<div class="container">
    <header class="header">
        <div class="flex">
            <div class="top-bar w-full">
                <div class="content left-content">
                    <img src = "/image/skfly.png" width="30px" height="30px">
                </div>
                <div class="content center-content">
                    <b>루키증권</b>
                </div>
                <div class="content right-content">
                    <a id="logout" class="logout">로그아웃</a>
                    <a id="login" href="/login" class="logout">로그인</a>
                    <p id="login_flag" style="display: none;">${login}</p>
                    <a id="qna" href="/qna" class="qna">Q&A</a>
                </div>
            </div>
        </div>
        <div class="search" style="float:center;">
            <div class="search-container">
                <input type="text" id="search-input" placeholder="검색...">
                <button id="search-button">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="search-icon">
                        <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
                    </svg>                    </button>
            </div>
        </div>
    </header>
    <nav class="tab-menu">
        <button class="tab" id="tab-myStocks" onclick="showTab('myStocks')">내 주식</button>
        <button class="tab" id="tab-todayStocks" onclick="showTab('todayStocks')">종목</button>
        <button class="tab" id="tab-news" onclick="showTab('news')">공지사항</button>
    </nav>


    <main>
        <section id="myStocks" class="tab-content">
            <a href="/mypage" class="alert-section">
                <div class="text">
                    <p>마이페이지에서 ${user_nm}님의 정보를 확인하세요<br></p>
                    <button>마이페이지</button>
                </div>
                <div class="button-wrapper">
                    <button class="add-button" onclick="event.preventDefault();">
              <span class="plus-icon"><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
              </svg>
              </span>
                    </button>
                </div>
            </a>
            <div id="recently_viewed_stocks" class="recent-stocks">
                <h2>최근 본 주식</h2>
                <div class="recent-stocks-list">
                    <!-- 최근 본 주식 아이템을 여기에 추가 -->
                    <div class="stock-item">
                        <span class="stock-name">Apple Inc.</span>
                    </div>
                    <div class="stock-item">
                        <span class="stock-name">Amazon.com Inc.</span>
                    </div>
                    <div class="stock-item">
                        <span class="stock-name">Alphabet Inc.</span>
                    </div>
                    <!-- 기타 주식 아이템을 추가할 수 있습니다 -->
                </div>
            </div>
            <br>
            <a href="/community" class="alert-section">
                <div class="text">
                    <p>커뮤니티에서 정보를 공유하세요.<br></p>
                    <button>커뮤니티 페이지</button>
                </div>
                <div class="button-wrapper">
                    <button class="add-button" onclick="event.preventDefault();">
              <span class="plus-icon"><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
              </svg>
              </span>
                    </button>
                </div>
            </a>
        </section>



        <section id="todayStocks" class="tab-content">

            <div class="search-result-section" style="display: none;">
                <h2>검색 결과</h2>
                <ol id="search_result" class="search-result">
                </ol>
            </div>

            <div class="indices-section">
                <h2>주요 지수</h2>
                <div class="indices">
                    <div class="index-item" id="kospi">
                        <p class="index-name">코스피</p>
                        <p class="index-value" >2,755.11</p>
                        <p class="index-change negative">-1.98 (-0.07%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item" id="kosdaq">
                        <p class="index-name">코스닥</p>
                        <p class="index-value">911.25</p>
                        <p class="index-change negative">-4.84 (-0.5%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item" id = "exchange_rate">
                        <p class="index-name">환율</p>
                        <p class="index-value">1,352.75</p>
                        <p class="index-change positive">+14.15 (1.0%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item" id = "nasdaq">
                        <p class="index-name">나스닥</p>
                        <p class="index-value">16,315.70</p>
                        <p class="index-change negative">-68.77 (0.4%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item" id = "snp500">
                        <p class="index-name">S&P 500</p>
                        <p class="index-value">5,203.58</p>
                        <p class="index-change negative">-14.61 (0.2%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                </div>
            </div>
            <div class="popular-stocks-section">
                <h2>종목 리스트</h2>
                <ol id="stock_list" class="stock-list">

                </ol>
            </div>
        </section>

        <section id="news" class="tab-content hidden">
            <%
                List<NoticeReq> noticeList = (List<NoticeReq>) request.getAttribute("noticeList");

                if (noticeList != null) {
                    for (NoticeReq notice : noticeList) {
                        java.util.Date date = notice.getNOTICE_DATE();
                        SimpleDateFormat sdf = new SimpleDateFormat("M월 d일", Locale.KOREA);
                        String formattedDate = sdf.format(date);
            %>
            <div class="announcement">
                <a href="notice-detail?noticeNo=<%= notice.getNOTICE_NO() %>"><h2 class="NOTICE_TITLE"><%= notice.getNOTICE_TITLE() %></h2></a>
                <p class="NOTICE_DATE"><%= formattedDate %></p>
            </div>
            <%
                    }
                }
            %>
            <c:set var="accessLevel" value="${sessionScope.access_level}" />
            <c:if test="${accessLevel != null && accessLevel == 1}">
                <a href="/noticewrite" class="text">
                    <button>글쓰기</button>
                </a>
            </c:if>

            <!-- 가운데 정렬을 위한 컨테이너 -->
            <div class="center-container">
                <!-- 페이지네이션 컨테이너 -->
                <div class="pagination-container">
                    <div class="pagination">
                        <a href="#" class="page-link">&laquo;</a>
                        <a href="#" class="page-link">&lt;</a>
                        <a href="#" class="page-link active">1</a>
                        <a href="#" class="page-link">&gt;</a>
                        <a href="#" class="page-link">&raquo;</a>
                    </div>
                </div>
            </div>
        </section>

	<input type="hidden" id ="hidde" value="">
    </main>
    <footer class="descript">
        <p>루키증권에서 제공하는 투자 정보는 고객의 투자 판단을 위한 단순 참고용일뿐, 투자 제안 및 권유·종목 추천을 위해 작성된 것이 아닙니다.
            <br><br>고객센터 | 투자 유의사항 | <b>개인정보처리방침</b>
            <br>이용자권리 및 유의사항 | 신용정보 활용체제
            <br><br>꼭 알아두세요</p>
    </footer>
</div>

</body>
</html>