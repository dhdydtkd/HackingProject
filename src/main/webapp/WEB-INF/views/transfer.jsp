<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="/css/transfer.css">
<script src="/js/transfer.js"></script>
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

<script src="/js/rsa/jsbn.js"></script>
<script src="/js/rsa/prng4.js"></script>
<script src="/js/rsa/rng.js"></script>
<script src="/js/rsa/rsa.js"></script>

<script type="text/javascript">
  $(document).ready(function(){

    $('#submit').click(function() {
      var confirmFlag = confirm("정말 송금 하시겠습니까?");
      if(confirmFlag){
        const rsa = new RSAKey();
        rsa.setPublic($('#RSAModulus').val(),$('#RSAExponent').val());
        let data = {
          name : $('#name').val()
          , account_number : $('#account_number').val()
          , price : parseInt($('#price').val().replace(/,/g, '').replace('원', ''))
          , transfer_bankagency : $('#transfer_bankagency').find("option:selected").text()
        }
        let e2eData = rsa.encrypt(JSON.stringify(data));
        $.ajax({
          url: '/mypage/send', // 컨트롤러 경로를 지정하세요.
          type: 'POST',
          contentType: 'application/json',
          data: e2eData, // 데이터를 JSON 형식으로 전송

          success: function(response) {
            alert(response.body);
          },
          error: function(xhr, status, error) {
            // 오류 발생 시 실행될 코드
            alert('오류 발생: ' + error);
          }
        });
      }else{

      }
    });


    $('#price').on('input', function() {
      var input = $(this).val();
      var filteredInput = input.replace(/,/g, '');
      filteredInput = filteredInput.replace(/[^\d]/g, '');
      filteredInput = filteredInput.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
      $(this).val(filteredInput+"원");
    });

    $('#account_number').on('input', function() {
      var input = $(this).val();
      var filteredInput = input.replace(/[^0-9-]/g, '');
      $(this).val(filteredInput);
    });

    $('#back').click(function() {
      window.history.back();
    });
  });

</script>
  <div class="container">
    <div class="card">
      <div style="display: none;" class="col-12 col-12-xsmall">
        공개키 : RSAModulus<input type="text" id="RSAModulus" value="${RSAModulus}" readonly/>
      </div>
      <div style="display: none;" class="col-12 col-12-xsmall">
        공개키 : RSAExponent<input type="text" id="RSAExponent" value="${RSAExponent}" readonly/>
      </div>
      <div class="header">
        <div id="back" class="left"><i class="material-icons">arrow_back</i></div>
        <div class="center">송금</div>
        <div class="right"></div>
      </div>
      <div class="form-box">
        <form action="transferForm" method="post">
          <div class="form-row">
            <p>은행</p>
            <select id="transfer_bankagency">
              <option value="" selected disabled>은행 선택</option>
              <option value="option1">카카오뱅크</option>
              <option value="option2">신한은행</option>
              <option value="option3">우리은행</option>
              <option value="option4">KB국민은행</option>
              <option value="option5">NH농협은행</option>
              <option value="option6">RK루키은행</option>
            </select>
          </div>
          <div class="form-row">
            <p>이름</p>
            <input id="name" type="text" id="TRANSFER_NAME" placeholder="받는 분">
          </div>
          <div class="form-row">
            <p>계좌번호</p>
            <input id="account_number" type="text" id="TRANSFER_DEPO" placeholder="계좌번호 입력">
          </div>
          <div class="form-row">
            <p>이체 금액</p>
            <input id="price" type="text" id="TRANSFER_AMOUNT" placeholder="이체 금액">
          </div>
          <div class="form-row">
            <input id="submit" type="button" value="이체하기">
          </div>
        </form>
      </div>

    </div>
  </div>

</body>
</html>
