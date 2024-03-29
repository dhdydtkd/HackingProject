<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>

<html>
<head>
    <title>Reve Spac Site</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="/css/main.css" />
</head>
<script>
    console.log("chart test");
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>

<body class="is-preload">
<style>
    /* 그래프 제목 숨기기 */
    #main {
        position: relative;
    }

    #main .chartjs-size-monitor,
    #main .chartjs-size-monitor-expand,
    #main .chartjs-size-monitor-shrink {
        display: none !important;
    }

</style>
<script type="text/javascript">
    // 초기 데이터
    var initialData = {
        labels: [],
        datasets: [{
            label: 'Test Dataset',
            fill: false,
            data: [], // 초기 데이터 배열
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1
        }]
    };

    // 최대 표시할 데이터 수
    var maxDataPoints = 60;

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
                            max: 25*4 // y 축의 최대값 설정
                        },
                        gridLines: {
                            display: false
                        }
                    }]
                }
            }
        });

        // 초기 데이터를 생성합니다.
        var currentTime = new Date();
        for (var i = maxDataPoints - 1; i >= 0; i--) {
            var newDataPoint = Math.floor(Math.random() * 30) + 10;
            var newTime = new Date(currentTime);
            newTime.setSeconds(currentTime.getSeconds() - i);
            var newLabel = newTime.getHours() + ':' + newTime.getMinutes() + ':' + newTime.getSeconds();

            myChart.data.labels.push(newLabel);
            myChart.data.datasets[0].data.push(newDataPoint);
        }

        // 차트 업데이트
        myChart.update();

        // 실시간 데이터 업데이트 함수
        function updateChartData() {
            // 새로운 데이터 생성
            var newDataPoint = Math.floor(Math.random() * 30) + 10;
            // 현재 시간을 레이블로 추가
            var currentTime = new Date();
            var newLabel = currentTime.getHours() + ':' + currentTime.getMinutes() + ':' + currentTime.getSeconds();

            // 데이터셋에 새로운 데이터 추가
            myChart.data.labels.push(newLabel);
            myChart.data.datasets[0].data.push(newDataPoint);

            // 최대 데이터 개수를 넘어가면 가장 오래된 데이터 제거
            if (myChart.data.labels.length > maxDataPoints) {
                myChart.data.labels.shift();
                myChart.data.datasets[0].data.shift();
            }

            // 차트 업데이트
            myChart.update();
        }

        // 2초마다 데이터 업데이트 함수 호출
        setInterval(updateChartData, 1000);
    });
</script>



</body>
</html>
<!-- Wrapper -->
<div id="wrapper">
    <!-- Main -->
    <div id="main">
        <div class="inner">
            <!-- Header -->
            <div style="width: 900px; height: 500px;">
                <!--차트가 그려질 부분-->
                <canvas id="myChart"></canvas>
            </div>
        </div>
    </div>
    <!-- Sidebar -->
    <jsp:include page="test_sidebar.jsp" flush="false"/>
</div>
<!-- Scripts -->
<script src="/js/jquery.min.js"></script>
<script src="/js/browser.min.js"></script>
<script src="/js/breakpoints.min.js"></script>
<script src="/js/util.js"></script>
<script src="/js/main.js"></script>
</body>
</html>
