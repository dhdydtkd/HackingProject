package com.example.hackingproject.config.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RestInterface {

    // 반환 타입은 Call<타입>의 제네릭 형태
    @GET("directions")
    Call<Object> getWayDirections(
            @Header("Authorization") String token,
            @Header("Content-Type") String content,
            @Query("origin") String origin,
            @Query("destination") String destination
    );

    @GET("getStockMarketIndex")
    Call<Object> getStockIndex(
            @Query("serviceKey") String serviceKey,
            @Query("resultType") String resultType,
            @Query("pageNo") int pageNo,
            @Query("numOfRows") int numOfRows,
            @Query("likeIdxNm") String idxNm
    );

}
