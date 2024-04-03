<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>루키증권</title>
    <link rel="stylesheet" href="/css/stocks.css" />
    <!-- Scripts -->
    <script src="/js/jquery.min.js"></script>
    <script src="/js/browser.min.js"></script>
    <script src="/js/breakpoints.min.js"></script>
    <script src="/js/util.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/common.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;200;300;400;500;600;700;800;900&display=swap"
      rel="stylesheet"
    />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css" rel="stylesheet" />


  </head>
<style>
    /* 그래프 제목 숨기기 */
    #myChart {
        position: relative;
    }

    #myChart .chartjs-size-monitor,
    #myChart .chartjs-size-monitor-expand,
    #myChart .chartjs-size-monitor-shrink {
        display: none !important;
    }

</style>
<script>

        function setCurrent() {
            var pri = $('#stcok_price').text(); // 오타 수정: 'stcok_price' -> 'stock_price'
            pri = pri.replace("원", "");
            $('#PRICES').text(pri); // 이것은 id가 'PRICES'인 요소의 텍스트를 설정합니다.
            $('#PRICE').text(pri); // 이것은 id가 'PRICE'인 다른 요소의 텍스트를 설정합니다.
        }

 
</script>

  <script type="text/javascript">
      // 초기 데이터
      var initialData = {
          labels: [],
          datasets: [{
              // label: 'Test Dataset',
              fill: false,
              data: [], // 초기 데이터 배열
              backgroundColor: 'rgba(99,130,255,0.2)',
              borderColor: 'rgb(79,115,255)',
              // backgroundColor: 'rgba(255, 99, 132, 0.2)',
              // borderColor: 'rgba(255, 99, 132, 1)',
              borderWidth: 1
          }]
      };

      // 최대 표시할 데이터 수
      var maxDataPoints = 60;

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

      document.addEventListener('DOMContentLoaded', function() {
          var context = document
              .getElementById('myChart')
              .getContext('2d');
          // 초기 데이터를 포함한 차트 생성
          var myChart = new Chart(context, {
              type: 'line',
              data: initialData,
              options: {
                  legend: {
                      display: false
                  },
                  tooltips: {
                      callbacks: {
                          label: function(tooltipItem) {
                              return tooltipItem.yLabel;
                          }
                      }
                  },
                  scales: {
                      xAxes: [{
                          display: false, // x축 눈금 제거
                          gridLines: {
                              display: false
                          }
                      }],
                      yAxes: [{
                          display: false, // y축 눈금 제거
                          ticks: {
                              beginAtZero: true,
                              display: false, // y축 눈금 제거
                              min: 0, // y 축의 최대값 설정
                              max: 0 // y 축의 최대값 설정
                          },
                          gridLines: {
                              display: false
                          }
                      }]
                  },
                  animation: {
                      onComplete: function() {
                          // 차트가 그려진 후에 실행할 코드 작성
                          console.log("chart onComplete")

                      }
                  }

              }
          });
          var stock_code = $('#stock_code').text();
          // 초기 데이터를 생성합니다.
          $.ajaxGET("stock/stock?stockCode="+stock_code, null, function(result){
              if (result.state.code == "0000") {
                  var stockbasePrice = 0;
                  for(let j = 0 ;j<stockBaseList.length; j++){
                      if(stockBaseList[j].name == result.body.stockDetail.STOCK_CODE){
                          stockbasePrice = stockBaseList[j].price;
                          break;
                      }
                  }
                  $('#buy_name').text(result.body.stockDetail.STOCK_NAME);
                  $('#sell_name').text(result.body.stockDetail.STOCK_NAME);
                  var percentage = (result.body.stockDetail.STOCK_PRICE / stockbasePrice) * 100 -100;
                  if(percentage<0){
                      $('#persent').text(percentage.toFixed(2)+"%");
                      var element = document.getElementById("persent");
                      element.classList.remove("text-red-500");
                      element.classList.add("text-blue-500");
                      myChart.data.datasets[0].backgroundColor = 'rgba(99,130,255,0.2)';
                      myChart.data.datasets[0].borderColor = 'rgb(79,115,255)';
                  }else{
                      $('#persent').text("+"+percentage.toFixed(2)+"%");
                      var element = document.getElementById("persent");
                      element.classList.remove("text-blue-500");
                      element.classList.add("text-red-500");
                      myChart.data.datasets[0].backgroundColor = 'rgba(255, 99, 132, 0.2)';
                      myChart.data.datasets[0].borderColor = 'rgba(255, 99, 132, 1)';
                  }

                  var stockMinuteData = result.body.stockMinute;
                  $('#stcok_price').text(formattedString(result.body.stockDetail.STOCK_PRICE)+"원");
					
                  var now_stock_price = result.body.stockDetail.STOCK_PRICE;
                  myChart.options.scales.yAxes[0].ticks.min = now_stock_price-now_stock_price/5;
                  myChart.options.scales.yAxes[0].ticks.max = now_stock_price+now_stock_price/3;
                  for (var i = 0; i < stockMinuteData.length; i++) {
                      var newDataPoint = stockMinuteData[i].STOCK_PRICE;

                      myChart.data.labels.push('');
                      myChart.data.datasets[0].data.push(newDataPoint);
                  }
                  max = 0;
                  min = 99999999;
                  for(let i=0;i<myChart.data.datasets[0].data.length;i++){
                      if(max<myChart.data.datasets[0].data[i]){
                          max = myChart.data.datasets[0].data[i];
                      }
                      if(min>myChart.data.datasets[0].data[i]){
                          min = myChart.data.datasets[0].data[i];
                      }
                  }
                  $('#max_price').text(formattedString(max)+"원");
                  $('#min_price').text(formattedString(min)+"원");
                  myChart.update();
              }
          });

          // // 실시간 데이터 업데이트 함수
          function updateChartData() {
              // 새로운 데이터 생성
              $.ajaxGET("stock/newstock?stockCode="+stock_code, null, function(result){
                  if (result.state.code == "0000") {
                      var newStockData = result.body.stockNewDetail;

                      var stockbasePrice = 0;
                      for(let j = 0 ;j<stockBaseList.length; j++){
                          if(stockBaseList[j].name == newStockData.STOCK_CODE){
                              stockbasePrice = stockBaseList[j].price;
                              break;
                          }
                      }
                      var percentage = (newStockData.STOCK_PRICE / stockbasePrice) * 100 -100;
                      if(percentage<0){
                          $('#persent').text(percentage.toFixed(2)+"%");
                          var element = document.getElementById("persent");
                          element.classList.remove("text-red-500");
                          element.classList.add("text-blue-500");
                          myChart.data.datasets[0].backgroundColor = 'rgba(99,130,255,0.2)';
                          myChart.data.datasets[0].borderColor = 'rgb(79,115,255)';
                      }else{
                          $('#persent').text("+"+percentage.toFixed(2)+"%");
                          var element = document.getElementById("persent");
                          element.classList.remove("text-blue-500");
                          element.classList.add("text-red-500");
                          myChart.data.datasets[0].backgroundColor = 'rgba(255, 99, 132, 0.2)';
                          myChart.data.datasets[0].borderColor = 'rgba(255, 99, 132, 1)';
                      }


                      $('#stcok_price').text(formattedString(newStockData.STOCK_PRICE)+"원");
					  setCurrent();
                      var newDataPoint = newStockData.STOCK_PRICE;
                      // 현재 시간을 레이블로 추가
                      myChart.data.datasets[0].data.push(newDataPoint);

                      // 최대 데이터 개수를 넘어가면 가장 오래된 데이터 제거
                      if (myChart.data.datasets[0].data.length > maxDataPoints) {
                          myChart.data.datasets[0].data.shift();
                      }
                      max = 0;
                      min = 99999999;
                      for(let i=0;i<myChart.data.datasets[0].data.length;i++){
                          if(max<myChart.data.datasets[0].data[i]){
                              max = myChart.data.datasets[0].data[i];
                          }
                          if(min>myChart.data.datasets[0].data[i]){
                              min = myChart.data.datasets[0].data[i];
                          }
                      }
                      $('#max_price').text(formattedString(max)+"원");
                      $('#min_price').text(formattedString(min)+"원");
                      myChart.update();
                  }
              });
          }
          
          intervalId = setInterval(updateChartData, 2000);
          window.addEventListener("blur", stopInterval);
          function stopInterval() {
              clearInterval(intervalId);
          }
      });
      
  </script>
  <script>
  
  $(document).ready(function(){
      $('#BUYINFO').click(function() {
    	  setCurrent();
      });
  });
      $(document).ready(function(){
          $('#SELLINFO').click(function() {
        	  setCurrent();
              var USERID = $('#USER_ID').val();
              var STOCK = setCompany(); // setCompany() 함수는 현재 코드에서 정의되지 않았지만, 이 함수가 적절한 값을 반환한다고 가정합니다.
              $.ajax({
                  url: '/haveStock',
                  type: 'POST',
                  contentType: 'application/json',
                  data: JSON.stringify({ userId: USERID, stock: STOCK }),

                  success: function(response){
                      // 서버로부터 응답 받은 후 처리
                      // response 객체에서 userId와 unit을 추출하여 alert로 표시
                      //alert("Success: " + response.userId + ", Unit: " + response.unit);
                      document.getElementById("OWN").innerHTML = "보유 주식: "+ "<span id='OWNN'>" +response.unit + "</span>";

                  },
                  error: function(xhr, status, error){
                      // 오류 발생 시 처리
                      alert("Error: " + error);
                  }
              });
          });
          $('#buyButton').click(function() {
        	  event.preventDefault();
              var PRICE = $('#PRICE').text().replace(",", "");;
              var UNIT = $('#UNIT').val();
              var USERID = $('#USER_ID').val();
              //alert(USERID);
              //var company = document.getElementById('companyName').textContent;
              var STOCK = document.getElementById('stock_code').textContent;
              //alert(PRICE);

              //alert(STOCK);

              $.ajax({
                  url: '/detailBuy', // 컨트롤러 경로를 지정하세요.
                  type: 'POST',
                  contentType: 'application/json',
                  data: JSON.stringify({ stock: STOCK, price: PRICE, userId: USERID, unit:UNIT }), // 데이터를 JSON 형식으로 전송

                  success: function(response) {
                      // 성공 시 실행될 코드. response는 컨트롤러에서 반환한 데이터입니다.
                      alert(response.MSG);
                  },
                  error: function(xhr, status, error) {
                      // 오류 발생 시 실행될 코드
                      alert('오류 발생: ' + error);
                  }
              });
          });
          $('#sellButton').click(function() {
        	  event.preventDefault();
              var PRICE = $('#PRICES').text().replace(",", "");;
              var UNIT = $('#UNITS').val();
              var USERID = $('#USER_ID').val();
              var OWN = $('#OWNN').text();
              //alert(USERID);
              //var company = document.getElementById('companyName').textContent;
              var STOCK = document.getElementById('stock_code').textContent;
              //alert(STOCK);
			  
              parseInt(OWN, 10);
			  
			 if (OWN < UNIT){
				  
				  return alert("보유 개수 보다 많이 매도 할 수 없습니다.");			  
			  }

              $.ajax({
                  url: '/detailSell', // 컨트롤러 경로를 지정하세요.
                  type: 'POST',
                  contentType: 'application/json',
                  data: JSON.stringify({ stock: STOCK, price: PRICE, userId: USERID, unit:UNIT }), // 데이터를 JSON 형식으로 전송

                  success: function(response) {
                      // 성공 시 실행될 코드. response는 컨트롤러에서 반환한 데이터입니다.
                      alert('판매완료');
                  },
                  error: function(xhr, status, error) {
                      // 오류 발생 시 실행될 코드
                      alert('오류 발생: ' + error);
                  }
              });
          });
      });
      function setCompany(){
          var STOCK=$('#stock_code').text();
          
          return STOCK
      }
      function clickButton(buttonType) {
          const chartButton = document.getElementById("chartButton");
          const communityButton = document.getElementById("communityButton");

          // 모든 버튼의 밑줄을 초기화합니다.
          chartButton.classList.remove("border-b-2", "border-black");
          communityButton.classList.remove("border-b-2", "border-black");

          // 선택된 버튼에만 밑줄 스타일을 추가합니다.
          if (buttonType === "chart") {
              chartButton.classList.add("border-b-2", "border-black");
          } else if (buttonType === "community") {
              communityButton.classList.add("border-b-2", "border-black");
          }
      }
  </script>

  <body>
    <header class="px-5 mt-3 flex justify-between">
        <button onclick="window.history.back();"><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18" />
          </svg>
          </button>
        <div class="space-x-2">
            <button><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z" />
              </svg>
              </button>
            <button><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0" />
              </svg>
              </button>
            <button><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
              </svg>
              </button>
        </div>
    </header>
    <main class="mt-16 px-6" >
      <div class="flex items-center flex-col">
        <div class="font-semibold text-3xl">${stockName}</div>
        <p id="stock_code" style="display: none;">${stockCode}</p>
        <input type='hidden' id="USER_ID" value = "admin">
        <div class="text-3xl mt-7 font-bold"><span id="stcok_price"></span>
            <span id="persent" class="text-red-500 inline-block text-xl ml-3"></span>
        </div>
      </div>
      <div class="flex justify-between mt-10 py-3">
        <a
          href="현재 페이지(주식 상세창)"
          id="chartButton"
          onclick="clickButton('chart')"
          class="w-full text-center py-3 border-b-2 border-black"
        >
          차트
        </a>
        <a
        href="커뮤니티 페이지"
          id="communityButton"
          onclick="clickButton('community')"
          class="w-full text-center py-3 "
        >
          커뮤니티
        </a>
      </div>
        <div class="flex justify-end mt-10 ">
            <div class="border border-black rounded">
                <button class="border-r border-black py-1 px-2">1분</button>
                <button class="border-r border-black py-1 pl-1 pr-2">1시간</button>
                <button class="border-r border-black py-1 pl-1 pr-2">하루</button>
                <button class="border-r border-black py-1 pl-1 pr-2">일주일</button>
                <button class="border-r border-black py-1 pl-1 pr-2">한달</button>
                <button class="border-r border-black py-1 pl-1 pr-2">1년</button>
            </div>
        </div>
        <div class="mt-2 pr-20 pl-5 h-96">
            <canvas id="myChart"></canvas>
        </div>
        <div class="flex text-center text-xl mt-5">
            <div class="w-full">
                <div>최고가</div>
                <div id="max_price" class="text-red-500 font-semibold mt-1">0원</div>
            </div>
            <div class="w-full">
                <div>최저가</div>
                <div id="min_price" class="text-blue-500 font-semibold mt-1">0원</div>
            </div>
        </div>
      <div class="fixed flex space-x-5 w-full pr-12 bottom-0 bg-white">
        <button id='BUYINFO'
          data-modal-target="buy-modal" data-modal-toggle="buy-modal"
          class="bg-red-500 w-full mb-5 py-3 text-center text-white rounded-lg font-semibold"
        >
          구매하기
        </button>
        <button
        data-modal-target="sell-modal" data-modal-toggle="sell-modal" id="SELLINFO"
          class="bg-blue-500 w-full mb-5 py-3 text-center text-white rounded-lg font-semibold"
        >
          판매하기
        </button>
      </div>
    </main>
  </body>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>
  <div id="buy-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-2xl max-h-full">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow">
            <div class="flex flex-col w-full p-4 ">
                <div class="text-center">
                    <h1 id="buy_name" class="text-sm text-gray-500" id="companyName" >삼성전자</h1>
                    <div class="text-xl font-semibold">구매 하기</div>
                    <form class="divide-y">
                        <div class="flex items-center justify-between py-2">
                            <div>희망 가격</div>
                            <div>
                                <span id="PRICE"> </span><span>원</span>
                            </div>
                        </div>
                        <div class="flex items-center justify-between py-2">
                            <div>주식 개수</div>
                            <div>
                                <input id="UNIT"class="border-none text-right focus:ring-0 focus:border-none focus:outline-none" type="number" placeholder="1" min="0" />개
                            </div>
                        </div>
                        <button data-modal-hide="buy-modal" id="buyButton" class="w-full bg-red-500 mt-3 text-white py-2 rounded-lg">구매하기</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  </div>
  <div id="sell-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-2xl max-h-full">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow">
            <div class="flex flex-col w-full p-4 ">
                <div class="text-center relative">
            
                    <h1 id="sell_name" class="text-sm text-gray-500" id="companyNames" >삼성전자</h1>
                    <div class="text-xl font-semibold">판매 하기</div>
                 
                    <div id="OWN" class="absolute text-gray-500 top-0 right-0">보유주식</div>
                    
                    <from class="divide-y">
                        <div class="flex items-center justify-between py-2">
                            <div>시장 가격</div>
                            <div>
                                <span id="PRICES"> </span><span>원</span>
                            </div>
                        </div>
                        <div class="flex items-center justify-between py-2">
                            <div>주식 수</div>
                            <div>
                                <input id="UNITS" class="border-none text-right focus:ring-0 focus:border-none focus:outline-none" type="number" placeholder="1" min="0" />개
                            </div>
                        </div>
                        <button data-modal-hide="sell-modal" id="sellButton" type="submit" class="w-full bg-blue-500 mt-3 text-white py-2 rounded-lg">판매하기</button>
                    </from>
                </div>
            </div>
        </div>
    </div>
  </div>
</div>
</html>
    