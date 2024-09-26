document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.toggle-button');
    
    buttons.forEach(button => {
      button.addEventListener('click', function() {
        buttons.forEach(btn => btn.classList.remove('active'));
        this.classList.add('active');
      });
    });
  });

  document.addEventListener('DOMContentLoaded', function() {
    // 'TRANSFER_AMOUNT'라는 ID를 가진 요소를 찾습니다.
    var amountInput = document.getElementById('TRANSFER_AMOUNT');
  
    // 해당 요소에 'input' 이벤트 리스너를 추가합니다.
    amountInput.addEventListener('input', function(e) {
      // 입력값에서 쉼표를 제거하고 숫자가 아닌 문자를 삭제합니다.
      var value = e.target.value.replace(/,/g, '').replace(/[^\d]/g, '');
  
      // 숫자를 세 자리마다 쉼표를 찍어서 형식을 바꿉니다.
      e.target.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    });
  });