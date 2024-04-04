package com.example.hackingproject.stock.service;

import com.example.hackingproject.config.retrofit.RestInterface;
import com.example.hackingproject.config.retrofit.RetrofitAPI;
import com.example.hackingproject.dao.StockIndexDAO;
import com.example.hackingproject.stock.dto.StockIndexData;
import com.example.hackingproject.stock.vo.StockIndexVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("StockIndexService")
public class StockIndexService {
    @Autowired
    private StockIndexDAO stockIndexDAO;
    @Value("tTYOdnBsi7ux5dTKeNLFrC3QqWdgMt4Ib8CsGTYMxhbKqhE7wsqujAwl9wdo6rZH1hMGFMdEdXg8OBkDufc+8A==")
    private String STOCK_INDEX_API_KEY;

    @Autowired
    private RestInterface restService;


    //@Scheduled(fixedRate = 100000000)
    public void updateStockIndexList(){
        String[] search_word = {"코스피", "코스닥", "KRX", "KRX ESG", "코스피 100"};
        String[] index_string = {"KOSPI", "KOSDAQ", "NASDAQ", "S&P500", "EXCHANGE_RATE"};

        RestInterface apiInterface = RetrofitAPI.getApiClient().create(RestInterface.class);

        StockIndexData input_index = new StockIndexData();


        for(int i=0; i<search_word.length; i++){
            Call<Object> call = apiInterface.getStockIndex(
                    STOCK_INDEX_API_KEY,
                    "json",
                    1,1,
                    search_word[i]
            );
            try{
                Response<Object> response = call.execute();
                ObjectMapper objectMapper = new ObjectMapper();

                String body = objectMapper.writeValueAsString(response.body());

                TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
                Map<String,Object> result = objectMapper.readValue(body,typeReference);
                HashMap result_response = (HashMap) result.get("response");
                HashMap result_body = (HashMap) result_response.get("body");
                HashMap result_items = (HashMap) result_body.get("items");
                ArrayList result_item = (ArrayList) result_items.get("item");


                input_index = setStockData(result_item,index_string[i]);
                stockIndexDAO.updateStockIndex(input_index);
            }catch(Exception e){
                System.out.println(e);
            }
        }

    }

    public List<StockIndexVO> getStockIndexList(){
        List<StockIndexVO> index_list = new ArrayList<>();
        String[] index_string = {"KOSPI","KOSDAQ","NASDAQ","S&P500","EXCHANGE_RATE"};
        for(int i=0; i<index_string.length; i++){
            index_list.add(stockIndexDAO.getStockIndex(index_string[i]));
        }

        return index_list;
    }



    public StockIndexData setStockData(ArrayList<Map<String, Object>> items, String name){
        StockIndexData index =  new StockIndexData();
        Map<String, Object> item = items.get(0);
        index.setIndex_code(name);
        index.setIndex_num((String)item.get("clpr"));
        index.setIndex_change((String) item.get("vs"));
        index.setIndex_date((String) item.get("basDt"));
        index.setIndex_change_percent(convertNumber((String) item.get("fltRt")));

        if(index.getIndex_change() != null){
            if(index.getIndex_change().contains("-")){
                index.setIndex_updown("하락");
            }else{
                index.setIndex_updown("상승");
            }
        }
        return index;
    }

    public static String convertNumber(String number) {
        if (number.startsWith("-.")) {
            number = "-0" + number.substring(1); // '-' 뒤에 공백 추가
        } else if (number.startsWith(".")) {
            number = "0" + number; // 소수점 앞에 0 추가
        }
        return number+"%";
    }
}
