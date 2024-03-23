<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>

<html>
<head>
    <title>Reve Spac Site</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="/css/main.css" />
</head>
<!-- Scripts -->
<script src="/js/jquery.min.js"></script>
<script src="/js/browser.min.js"></script>
<script src="/js/breakpoints.min.js"></script>
<script src="/js/util.js"></script>
<script src="/js/main.js"></script>
<script src="/js/common.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=850c8520fc1c129f490a0bcb990a4477"></script>

<body class="is-preload">
<script>
    let makerList = [];
    let makerListLatLng = [];
    let wayMakerList = [];
    let polyline;
    $(() => {
        let container = document.getElementById('map');
        let options = {
            center: new kakao.maps.LatLng(37.32858949370699, 127.06839887854193),
            level: 3
        };
        let map = new kakao.maps.Map(container, options);

        // 지도에 클릭 이벤트를 등록합니다
        // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
        kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
            let latlng = mouseEvent.latLng;
            console.log(latlng);
            if(makerList.length<2){
                let newMarker = new kakao.maps.Marker({
                    // 지도 중심좌표에 마커를 생성합니다
                    position: mouseEvent.latLng
                });

                newMarker.setMap(map);
                makerList.push(newMarker);
                makerListLatLng.push(latlng);
                kakao.maps.event.addListener(newMarker, 'click', function() {
                    // 마커 위에 인포윈도우를 표시합니다
                    for (var i = 0; i < makerList.length; i++) {
                        if(makerList[i]==newMarker){
                            makerList[i].setMap(null);
                            makerListLatLng.splice(i, 1);
                            makerList.splice(i, 1);
                        }
                    }
                });
            }
        });
        $("#marker_delete").click(function() {
            polyline.setMap(null);
            for (var i = 0; i < makerList.length; i++) {
                makerList[i].setMap(null);
            }
            for (var i = 0; i < wayMakerList.length; i++) {
                wayMakerList[i].setMap(null);
            }

            makerListLatLng = [];
            makerList = [];
            wayMakerList = [];

        });

        $("#way_directions").click(function() {
            if(makerList.length!=2){
                return;
            }
            let latLngData = {
                startPointLat : makerListLatLng[0].La
                , startPointLng : makerListLatLng[0].Ma
                , endPointLat : makerListLatLng[1].La
                , endPointLng : makerListLatLng[1].Ma
            }
            $.ajaxPOST("map/waydirections", latLngData, function(result){
                console.log(result);
                console.log(result.body);
                console.log(result.body.wayDirectionsResult);
                console.log(result.body.wayDirectionsResult.latLngList);
                var latLngList = result.body.wayDirectionsResult.latLngList;
                var linePath = [];

                for (var i=0 ; i<latLngList.length ; i++) {
                    linePath.push(new kakao.maps.LatLng(latLngList[i].lat, latLngList[i].lng));
                }

                // 지도에 표시할 선을 생성합니다
                polyline = new kakao.maps.Polyline({
                    path: linePath, // 선을 구성하는 좌표배열 입니다
                    strokeWeight: 5, // 선의 두께 입니다
                    strokeColor: '#ff4444', // 선의 색깔입니다
                    strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                    strokeStyle: 'solid' // 선의 스타일입니다
                });
                // 지도에 선을 표시합니다
                polyline.setMap(map);
                /*
                var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
                var imageSize = new kakao.maps.Size(24, 35);
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
                for (var i=0 ; i<latLngList.length ; i++) {
                    console.log(latLngList[i]);
                    var markerPosition  = new kakao.maps.LatLng(latLngList[i].lat, latLngList[i].lng);
                    let newMarker = new kakao.maps.Marker({
                        // 지도 중심좌표에 마커를 생성합니다
                        position: markerPosition,
                        image : markerImage // 마커 이미지
                    });
                    newMarker.setMap(map);
                    wayMakerList.push(newMarker);
                }
                */
            });
        });
    })
</script>
<!-- Wrapper -->
<div id="wrapper">
    <!-- Main -->
    <div id="main">
        <div class="inner">
            <!-- Header -->
            <header id="header">
                <strong>Map</strong>
            </header>

            <!-- Banner -->
            <div class="content">
                <strong style="margin: auto">kakao Map API</strong>
                <div id="map" style="width:auto;height:500px;">
                </div>
            </div>
            <div style="margin-top: 20px" class="col-12">
                <ul class="actions">
                    <li><input type="button" id="marker_delete" value="마커지우기"/></li>
                    <li><input type="button" id="way_directions" value="길찾기"/></li>
                    <li><input type="button" id="cancel" value="cancel"/></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- Sidebar -->
    <jsp:include page="sidebar.jsp" flush="false"/>
</div>

</body>
</html>