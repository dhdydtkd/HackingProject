package com.example.hackingproject.config.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitAPI {
    private static final String BASE_URL = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {

        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .modules(new JavaTimeModule())
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // .client(client)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .build();
        }

        return  retrofit;
    }
}
