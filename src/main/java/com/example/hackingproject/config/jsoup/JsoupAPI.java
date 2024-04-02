package com.example.hackingproject.config.jsoup;
import com.example.hackingproject.stock.vo.StockIndexVO;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.lang.System;


public class JsoupAPI {

    private String BaseURL = "https://finance.naver.com/";
    private WebDriver driver;

    public JsoupAPI() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver\\chromedriver.exe");

    }


    public Document getHtmlResponse(String u) {

        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);

            // BaseURL에 GET 요청을 보내고 HTML 응답을 받아옴
            String url = BaseURL + u;
            driver.get(url);
            Document doc = Jsoup.parse(driver.getPageSource());
            // HTML 응답을 문자열로 변환하여 반환
            return doc;
        } finally {
            // 웹 드라이버 종료
            driver.quit();
        }
    }

    public StockIndexVO getKospiData(String id){
        Document html_result = getHtmlResponse("sise");
        Element kospiElement = html_result.getElementById(id);
        String temp="";
        StockIndexVO index = new StockIndexVO();
        if (id.equals("KOSPI")){
            index.setINDEX_CODE("KOSPI");
            index.setINDEX_NUM(html_result.getElementById("KOSPI_now").text());
            temp = html_result.getElementById("KOSPI_change").text();

        }

        else if(id.equals("KOSDAQ")){
            index.setINDEX_CODE("KOSDAQ");
            index.setINDEX_NUM(html_result.getElementById("KOSDAQ_now").text());
            temp = html_result.getElementById("KOSDAQ_change").text();
        }

        if(temp != ""){
            index.setINDEX_CHANGE(temp.split(" ")[0]);
            temp = temp.split(" ")[1];
            index.setINDEX_CHANGE_PERSENT(temp.split("%")[0]+"%");
            index.setINDEX_UPDOWN(temp.split("%")[1]);
        }
        return index;

    }
    public StockIndexVO getWorldData(String id){
        Document html_result = getHtmlResponse("world");
        StockIndexVO index = new StockIndexVO();

        if(id.equals("NASDAQ")){
            Element nasdaqElement = html_result.select("ul.data_lst#worldIndexColumn2 li.on").first();
            index.setINDEX_NUM(nasdaqElement.select("dd.point_status strong").text());
            index.setINDEX_CHANGE(nasdaqElement.select("dd.point_status em").text());
            index.setINDEX_CHANGE_PERSENT(nasdaqElement.select("dd.point_status span:eq(2)").text());
            index.setINDEX_UPDOWN(nasdaqElement.select("dd.point_status span:eq(3)").text());
            index.setINDEX_CODE("NASDAQ");
        }

        else if(id.equals("S&P500")){
            index.setINDEX_CODE("S&P500");
            Element nasdaqElement = html_result.select("ul.data_lst#worldIndexColumn3 li.on").first();
            index.setINDEX_NUM(nasdaqElement.select("dd.point_status strong").text());
            index.setINDEX_CHANGE(nasdaqElement.select("dd.point_status em").text());
            index.setINDEX_CHANGE_PERSENT(nasdaqElement.select("dd.point_status span:eq(2)").text());
            index.setINDEX_UPDOWN(nasdaqElement.select("dd.point_status span:eq(3)").text());
        }

        return index;
    }

    public StockIndexVO getExchangeRate(){
        Document html_result = getHtmlResponse("marketindex");
        StockIndexVO index = new StockIndexVO();

        Element exchangeList = html_result.getElementById("exchangeList");
        Element usdExchange = exchangeList.selectFirst("li.on");


        String currencyName = usdExchange.select("h3.h_lst").text();
        String exchangeRate = usdExchange.select("div.head_info span.value").first().text();
        String change = usdExchange.select("div.head_info span.change").first().text();
        String changeDirection = usdExchange.select("div.head_info span.blind").last().text();

        index.setINDEX_CODE("EXCHANGE_RATE");
        index.setINDEX_NUM(exchangeRate);
        index.setINDEX_CHANGE(change);
        index.setINDEX_UPDOWN(changeDirection);
        return index;
    }



}
