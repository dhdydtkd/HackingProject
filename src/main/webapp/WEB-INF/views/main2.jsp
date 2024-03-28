<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="/css/main2.css">
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
                <div class="search-container">
                    <input type="text" id="search-input" placeholder="검색...">
                    <button id="search-button">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="search-icon">
                            <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
                          </svg>                    </button>
                </div>
            </div>
            <div class="content right-content">
                <a href="#" class="logout">로그아웃</a>
            </div>
        </div>
    </div>
    <div class="logo-name" style="float: left;">
        <b>루키증권</b>
    </div>
  </header>
  <nav class="tab-menu">
    <button class="tab" id="tab-myStocks" onclick="showTab('myStocks')">내 주식</button>
    <button class="tab" id="tab-todayStocks" onclick="showTab('todayStocks')">오늘의 발견</button>
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
        <div class="recent-stocks">
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
            <h2>실시간 차트</h2>
            <ul>
                <li>
                    <p name="STOCK_NAME">콘텐츠2-1</p>
                    <p name="STOCK_PRICE">가격</p>
                </li>
                <li>
                    <p name="STOCK_NAME">콘텐츠2-2</p>
                    <p name="STOCK_PRICE">가격</p>
                </li>
                <li>
                    <p name="STOCK_NAME">콘텐츠2-3</p>
                    <p name="STOCK_PRICE">가격</p>
                </li>
                <li>
                    <p name="STOCK_NAME">콘텐츠2-4</p>
                    <p name="STOCK_PRICE">가격</p>
                </li>
                <li>
                    <p name="STOCK_NAME">콘텐츠2-5</p>
                    <p name="STOCK_PRICE">가격</p>
                </li>
            </ul>
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
<script src="/js/main2.js"></script>
</body>
</html>
