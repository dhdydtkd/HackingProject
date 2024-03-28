<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="/css/stocks.css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;200;300;400;500;600;700;800;900&display=swap"
      rel="stylesheet"
    />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css" rel="stylesheet" />
  </head>
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
      <div>
        <div class="font-semibold">파로스아이바이오</div>
        <div class="text-3xl font-bold">20,000원</div>
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
        <div class="mt-10 h-96 border border-black"></div>

      <div class="fixed flex space-x-5 w-full pr-12 bottom-0 bg-white">
        <button
          data-modal-target="buy-modal" data-modal-toggle="buy-modal"
          class="bg-red-500 w-full mb-5 py-3 text-center text-white rounded-lg font-semibold"
        >
          구매하기
        </button>
        <button
        data-modal-target="sell-modal" data-modal-toggle="sell-modal"
          class="bg-blue-500 w-full mb-5 py-3 text-center text-white rounded-lg font-semibold"
        >
          판매하기
        </button>
      </div>
    </main>
  </body>
  <script>
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
  <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>
  <div id="buy-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-2xl max-h-full">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow">
            <div class="flex flex-col w-full p-4 ">
                <div class="text-center">
                    <h1 class="text-sm text-gray-500">파로스아이바이오</h1>
                    <div class="text-xl font-semibold">구매 하기</div>
                    <from class="divide-y">
                        <div class="flex items-center justify-between py-2">
                            <div>희망 가격</div>
                            <div>
                                <input class="border-none text-right focus:ring-0 focus:border-none focus:outline-none" type="number" min="0" placeholder="1000" />원
                            </div>
                        </div>
                        <div class="flex items-center justify-between py-2">
                            <div>주식 개수</div>
                            <div>
                                <input class="border-none text-right focus:ring-0 focus:border-none focus:outline-none" type="number" placeholder="1" min="0" />개
                            </div>
                        </div>
                        <button data-modal-hide="buy-modal" type="submit" class="w-full bg-red-500 mt-3 text-white py-2 rounded-lg">구매하기</button>
                    </from>
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
                <div class="text-center">
                    <h1 class="text-sm text-gray-500">파로스아이바이오</h1>
                    <div class="text-xl font-semibold">판매 하기</div>
                    <from class="divide-y">
                        <div class="flex items-center justify-between py-2">
                            <div>희망 가격</div>
                            <div>
                                <input class="border-none text-right focus:ring-0 focus:border-none focus:outline-none" type="number" min="0" placeholder="1000" />원
                            </div>
                        </div>
                        <div class="flex items-center justify-between py-2">
                            <div>주식 수</div>
                            <div>
                                <input class="border-none text-right focus:ring-0 focus:border-none focus:outline-none" type="number" placeholder="1" min="0" />개
                            </div>
                        </div>
                        <button data-modal-hide="sell-modal" type="submit" class="w-full bg-blue-500 mt-3 text-white py-2 rounded-lg">판매하기</button>
                    </from>
                </div>
            </div>
        </div>
    </div>
  </div>
</div>
</html>
    