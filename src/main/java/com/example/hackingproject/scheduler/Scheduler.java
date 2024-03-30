package com.example.hackingproject.scheduler;

import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.dao.StockDAO;
import com.example.hackingproject.notice.dto.NoticeReq;
import com.example.hackingproject.stock.dto.StockData;
import com.example.hackingproject.stock.dto.StockHourData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class Scheduler {

    @Autowired
    private StockDAO stockDAO;

    @Value("${oys_scheduler}")
    private String oysScheduler ; // 개인키 session key


    // 오후 8시 0분 0초에 실행
//    @Scheduled(cron = "0 0 20 * * ?")
//    public void noticeSch() {
//        NoticeReq notice = new NoticeReq();
//        int noticeIndex = noticeDAO.getAddNoticeIndex();
//        notice.setTitle("게시판 테스트"+noticeIndex);
//        notice.setContext("스케쥴러로 생성한 게시글 "+noticeIndex+"번 입니다.");
//        notice.setCreateID(systemName);
//        noticeDAO.addSchedulerNotice(notice);
//    }

    int stockBaseValue_1 = 100;
    int stockBaseValue_2 = 1000;
    int stockBaseValue_3 = 1500;
    int stockBaseValue_4 = 22000;
    int stockBaseValue_5 = 100000;

    @Scheduled(fixedRate = 1000)
    public void stockDataScheduled() {
        StockData stock_1 = createStockData(1,"AAPL","Apple Inc.",2,1);
        StockData stock_2 = createStockData(2,"AMZN","Amazon.com Inc.",20,6);
        StockData stock_3 = createStockData(3,"FB","Meta Platforms Inc.",20,6);
        StockData stock_4 = createStockData(4,"GOOGL","Alphabet Inc.",200,100);
        StockData stock_5 = createStockData(5,"MSFT","Microsoft Corp.",1000,100);

//        stockDAO.addSchedulerStock(stock_1);
//        stockDAO.addSchedulerStock(stock_2);
//        stockDAO.addSchedulerStock(stock_3);
//        stockDAO.addSchedulerStock(stock_4);
//        stockDAO.addSchedulerStock(stock_5);
    }

    @Scheduled(fixedRate = 10000)
    public void test() {
        List<StockHourData> stockHourList = new ArrayList<StockHourData>();
        for (int i = 0; i < 60; i++) {
//            stockHourList.add(stockDAO.getStock1HourData(i*60));
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void stockDataDeleteScheduled() {
        stockDAO.deleteSchedulerStock();
        System.out.println("stockDataDeleteScheduled");
    }

    public StockData createStockData(int type, String code, String name, int price, int base_price){
        Random random = new Random();

        StockData base_stock = new StockData();
        base_stock.setStock_code(code);
        base_stock.setStock_name(name);
        int randomChange = random.nextInt(price) + base_price; // 1~5 사이의 랜덤 값 생성
        boolean add = random.nextBoolean(); // true 또는 false 랜덤 생성

        if(type==1){
            if (add) { stockBaseValue_1 += randomChange;}
            else { stockBaseValue_1 -= randomChange;}
            base_stock.setStock_price(stockBaseValue_1);
        }else if(type==2){
            if (add) { stockBaseValue_2 += randomChange;}
            else { stockBaseValue_2 -= randomChange;}
            base_stock.setStock_price(stockBaseValue_2);
        }else if(type==3){
            if (add) { stockBaseValue_3 += randomChange;}
            else { stockBaseValue_3 -= randomChange;}
            base_stock.setStock_price(stockBaseValue_3);
        }else if(type==4){
            if (add) { stockBaseValue_4 += randomChange;}
            else { stockBaseValue_4 -= randomChange;}
            base_stock.setStock_price(stockBaseValue_4);
        }else if(type==5){
            if (add) { stockBaseValue_5 += randomChange;}
            else { stockBaseValue_5 -= randomChange;}
            base_stock.setStock_price(stockBaseValue_5);
        }
        return base_stock;
    }
}
