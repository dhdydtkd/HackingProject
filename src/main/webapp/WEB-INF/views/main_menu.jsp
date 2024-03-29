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
<script>
    var intervalId = null


    $(() => {
        stockListSearch();
        intervalId = setInterval(stockListSearch, 2000); // 1000 밀리초 = 1초
        window.addEventListener("blur", stopInterval);

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
            //쿠키 삭제
            document.cookie = "SKJWTToken" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
            console.log("logout");
            alert("로그아웃 되었습니다.");
            location.reload();
        });

    });
    function stopInterval() {
        clearInterval(intervalId);
    }
    function stockListSearch(){
        console.log("stockListSearch");
        $.ajaxGET("stock/stocklist", null, function(result){
            if (result.state.code == "0000") {
                let stockList = result.body;
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

                for(let i = 0 ; i<stockList.length ; i++){
                    var stockbasePrice = 0;
                    for(let j = 0 ;j<stockBaseList.length; j++){
                        if(stockBaseList[j].name == stockList[i].STOCK_CODE){
                            stockbasePrice = stockBaseList[j].price;
                            break;
                        }
                    }
                    var percentage = calculatePercentage(stockbasePrice,stockList[i].STOCK_PRICE) -100;
                    var detailstockdata = "/detailstock?stockCode="+stockList[i].STOCK_CODE+"&stockName="+stockList[i].STOCK_NAME;
                    stockBodyHTML += "<a href="+detailstockdata+">";
                    stockBodyHTML += "<li>";
                    stockBodyHTML += "<span class='stock-number'>"+(i+1)+"</span>";
                    stockBodyHTML += "<p name='STOCK_NAME'>"+stockList[i].STOCK_NAME+"</p>";
                    stockBodyHTML += "<p name='STOCK_PRICE'>"+formattedString(stockList[i].STOCK_PRICE)+"원</p>";

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
                </div>
            </div>
        </div>
        <div class="search" style="float: center;">
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
            <a href="계좌_생성_페이지_URL" class="alert-section">
                <div class="text">
                    <p>마이페이지에서 내 정보를 확인하세요<br></p>
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
        </section>

        <section id="todayStocks" class="tab-content">
            <div class="indices-section">
                <h2>주요 지수</h2>
                <div class="indices">
                    <div class="index-item">
                        <p class="index-name">코스피</p>
                        <p class="index-value">2,755.11</p>
                        <p class="index-change negative">-1.98 (-0.07%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item">
                        <p class="index-name">코스닥</p>
                        <p class="index-value">911.25</p>
                        <p class="index-change negative">-4.84 (-0.5%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item">
                        <p class="index-name">환율</p>
                        <p class="index-value">1,352.75</p>
                        <p class="index-change positive">+14.15 (1.0%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item">
                        <p class="index-name">나스닥</p>
                        <p class="index-value">16,315.70</p>
                        <p class="index-change negative">-68.77 (0.4%)</p>
                        <!-- 그래프 이미지 또는 SVG가 들어갈 곳 -->
                        <div class="index-graph negative"><!-- 그래프 이미지 또는 SVG --></div>
                    </div>
                    <div class="index-item">
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
            <table class="news-table" >
                <thead>
                <tr>
                    <th scope="col">제목</th>
                    <th scope="col">작성자</th>
                    <th scope="col">일시</th>
                </tr>
                </thead>
                <tbody>
                <tr onclick="window.location='공지사항1_URL';">
                    <td name="NOTICE_TITLE">공지사항 1</td>
                    <td name="USER_ID">관리자</td>
                    <td name="NOTICE_DATE">2024.03.27</td>
                </tr>
                <tr onclick="window.location='공지사항2_URL';">
                    <td name="NOTICE_TITLE">공지사항 2</td>
                    <td name="USER_ID">관리자</td>
                    <td name="NOTICE_DATE">2024.03.27</td>
                </tr>
                <tr onclick="window.location='공지사항3_URL';">
                    <td name="NOTICE_TITLE">공지사항 3</td>
                    <td name="USER_ID">관리자</td>
                    <td name="NOTICE_DATE">2024.03.27</td>
                </tr>
                </tbody>
            </table>
            <a href="공지사항_페이지_URL" class="more-link">더보기</a>
        </section>


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
