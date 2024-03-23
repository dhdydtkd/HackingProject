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

<body class="is-preload">
<script>
	let currentPage = 1;
	let pageMaxCount = 2;
	let pageGroupConut = 5;
	let totalPage = 1;
	let totalCount = 0;

	$(() => {
		search(currentPage);
		$("#view_count").change(function() {
			let oldPageMaxCount = pageMaxCount;
			pageMaxCount = parseInt(this.value);
			currentPage = Math.ceil(oldPageMaxCount*currentPage/pageMaxCount);
			totalPage = Math.ceil(totalCount/pageMaxCount);
			if((currentPage*pageMaxCount)>totalCount){
				currentPage=totalPage;
			}
			search(currentPage);
		});
	});
	function search(selectPage){

		currentPage = selectPage;
		let pagingData = {
			currentPage: selectPage
			, pageMaxCount: pageMaxCount
		}
		$.ajaxPOST("notice/noticelist", pagingData, function(result){
			if (result.state.code == "0000") {
				let noticeList = result.body.noticeList;
				let searchData = result.body.searchData;
				totalCount = searchData.totalCount;
				totalPage = searchData.totalPage;

				let noticeBodyHTML = "";

				if(noticeList.length==0 && currentPage!=1){
					search(currentPage-1);
				}

				for(let i = 0 ; i<noticeList.length ; i++){
					noticeBodyHTML += "<tr>";
					noticeBodyHTML += "<td>"+(((selectPage-1)*pageMaxCount)+i+1)+"</td>";
					noticeBodyHTML += "<td>"+noticeList[i].title+"</td>";
					noticeBodyHTML += "<td>"+noticeList[i].context+"</td>";
					noticeBodyHTML += "<td>"+noticeList[i].createID+"</td>";
					noticeBodyHTML += "<td>"+noticeList[i].createDT+"</td>";
					noticeBodyHTML += "<td> <a type='button' name='notice_delete' id='"+noticeList[i].no+"' class='button small'>삭제</a></td>";

					noticeBodyHTML += "</tr>";
				}

				$('#table_body').empty();
				$('#table_body').append(noticeBodyHTML);
				paging(selectPage);
				$(document).off('click',"a[name='notice_delete']").on("click","a[name='notice_delete']",function(){
					$.ajaxPOST("notice/noticedelete", parseInt(this.id), function(result){
						if(result.state.code == "0000" || result.state.code == "0100"){
							if(result.state.desc!=null){
								alert(result.state.desc);
							}else{
								currentPage = Math.ceil(pageMaxCount*currentPage/pageMaxCount);
								totalPage = Math.ceil(totalCount/pageMaxCount);
								if((currentPage*pageMaxCount)>totalCount){
									currentPage=totalPage;
								}
								// 여기서 currentPage에 데이터가 없는걸 알아야함
								// -1을 하기엔 다른 사람이 한 20개를 지웠으면?
								search(currentPage);
							}
						}

					});
				});

			}
		});
	}
	function paging(currentPage) {

		$("#paging_ul").empty();

		if(totalPage<currentPage){
			currentPage=totalPage;
		}
		let last = currentPage+Math.ceil(pageGroupConut/2); //화면에 보여질 마지막 페이지 번호
		let first = currentPage-Math.ceil(pageGroupConut/2); //화면에 보여질 첫번째 페이지 번호

		if(last>totalPage){
			first -= last - totalPage;
			last -= last - totalPage;
		}

		if(first<=0){ //
			if(last<totalPage){
				last += 1 + (first*-1);
				if(last>totalPage){
					last = totalPage;
				}
			}
			first = 1;
		}

		let pageHtml = "";

		if(currentPage>1){
			pageHtml += "<li><a class='button' id='prev'> << </a></li>";
		}else{
			pageHtml += "<li><a class='button disabled' id='prev'> << </a></li>";
		}


		//페이징 번호 표시
		if(last<1){last=1;}//페이징 번호의 1은 무조건 있어야함.
		for (let i = first; i <= last; i++) {
			if (currentPage == i) {
				pageHtml +=
						"<li><a type='button' name='search_button' class='page active' id='" + i + "'>" + i + "</a></li>";
			} else {
				pageHtml += "<li><a type='button' name='search_button' class='page' id='" + i + "'>" + i + "</a></li>";
			}
		}
		if(currentPage<totalPage){
			pageHtml += "<li><a class='button' id='next'> >> </a></li>";
		}else{
			pageHtml += "<li><a class='button disabled' id='next'> >> </a></li>";
		}


		$("#paging_ul").append(pageHtml);
		$(document).off('click',"a[name='search_button']").on("click","a[name='search_button']",function(){
			search(parseInt(this.id));
		});
		$("#prev").off('click').on('click', function() {
			currentPage=Math.floor((currentPage-pageGroupConut)/pageGroupConut)*pageGroupConut+1;
			search(1);
		});
		$("#next").off('click').on('click', function() {
			search(totalPage);
		});
	}
</script>
<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
	<div id="main">
		<div class="inner">
			<!-- Header -->
			<header id="header">
				<strong>게시판</strong>
			</header>

			<!-- Banner -->
			<section id="banner">
				<div class="content">
					<h3>Table</h3>
					<div style="width: 20%">
						<select id="view_count">
							<option value="1">1개씩 보기</option>
							<option selected value="2">2개씩 보기</option>
							<option value="5">5개씩 보기</option>
							<option value="10">10개씩 보기</option>
						</select>
					</div>
					<div class="table-wrapper" style="margin-top: 20px">
						<table>
							<thead>
								<tr>
									<th width="7%">번호</th>
									<th width="15%">제목</th>
									<th width="33%">내용</th>
									<th width="15%">글쓴이</th>
									<th width="20%">생성시간</th>
									<th width="10%"></th>
								</tr>
							</thead>
							<tbody id="table_body">

							</tbody>
						</table>
						<ul id="paging_ul" class="pagination" style="text-align: center">

						</ul>
					</div>
				</div>
			</section>
		</div>
	</div>
	<!-- Sidebar -->
	<jsp:include page="sidebar.jsp" flush="false"/>
</div>
</body>
</html>
