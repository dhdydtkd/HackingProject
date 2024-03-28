function showTab(tabId) {
  // 모든 탭 콘텐츠를 숨김
  var tabs = document.querySelectorAll('.tab-content');
  for (var i = 0; i < tabs.length; i++) {
    tabs[i].style.display = 'none';
  }

  // 모든 탭 버튼의 'active' 클래스를 제거
  var tabButtons = document.querySelectorAll('.tab');
  for (var i = 0; i < tabButtons.length; i++) {
    tabButtons[i].classList.remove('active');
  }

  // 선택된 탭과 버튼에 'active' 클래스를 추가
  document.getElementById(tabId).style.display = 'block';
  document.getElementById('tab-' + tabId).classList.add('active');
}

// 페이지가 로드될 때 '내 주식' 탭을 기본으로 표시
window.addEventListener('DOMContentLoaded', () => showTab('myStocks'));
