package com.example.hackingproject.map.service;

import com.example.hackingproject.config.retrofit.RestInterface;
import com.example.hackingproject.config.retrofit.RetrofitAPI;
import com.example.hackingproject.map.dto.LatLngReq;
import com.example.hackingproject.map.vo.LatLng;
import com.example.hackingproject.map.vo.WayDirectionsResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.*;

@Service("MapService")
public class MapService {

    @Value("${kakao.rest.api.key}")
    private String KAKAO_REST_API_KEY ; // 개인키 session key

    @Autowired
    private RestInterface restService;
    public WayDirectionsResult wayDirections(LatLngReq latLngReq){

        WayDirectionsResult wayDirectionsResult = new WayDirectionsResult();

        RestInterface apiInterface = RetrofitAPI.getApiClient().create(RestInterface.class);

        String origin = latLngReq.getStartPointLat()+","+latLngReq.getStartPointLng();
        String destination = latLngReq.getEndPointLat()+","+latLngReq.getEndPointLng();
        Call<Object> call = apiInterface.getWayDirections(
                KAKAO_REST_API_KEY,
                "application/json",
                origin,
                destination
        );
        try {
            Response<Object> response = call.execute();

            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(response.body());

            TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
            Map<String, Object> result = objectMapper.readValue(body, typeReference);
            HashMap routes = (HashMap)((ArrayList)result.get("routes")).get(0);
            HashMap sections = (HashMap)((ArrayList)routes.get("sections")).get(0);
            ArrayList guidesList = (ArrayList)sections.get("guides");
            ArrayList roadsList = (ArrayList)sections.get("roads");

            Integer totalDistance = getTotalDistance(sections);
            Integer totalDuration = getTotalDuration(sections);

            wayDirectionsResult.setTotalDistance(totalDistance);
            wayDirectionsResult.setTotalDuration(totalDuration);
            List<LatLng> LatLngList = new ArrayList<LatLng>();

            for(int i=0;i<(roadsList.size());i++){
                ArrayList vertexesList = (ArrayList) ((HashMap)roadsList.get(i)).get("vertexes");
                for(int x=0;x<(vertexesList.size()-2);x+=2) {
                    LatLng latLng = new LatLng();
                    latLng.setLng((Double)vertexesList.get(x));
                    latLng.setLat((Double)vertexesList.get(x+1));
                    LatLngList.add(latLng);
                }
            }

            wayDirectionsResult.setLatLngList(LatLngList);
            System.out.println("Finsh");
        } catch (Exception e) {
            System.out.println(e);
        }
        return wayDirectionsResult;
    }
    public Integer getTotalDistance(HashMap sections){
        return (Integer)sections.get("distance");
    }
    public Integer getTotalDuration(HashMap sections){
        return (Integer)sections.get("duration");
    }

}
