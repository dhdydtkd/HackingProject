package com.example.hackingproject.map;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.map.dto.LatLngReq;
import com.example.hackingproject.map.vo.WayDirectionsResult;
import com.example.hackingproject.map.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/map")
public class MapAPIController {

    @Autowired
    private MapService mapService;

    @RequestMapping(method = RequestMethod.POST, path = "/waydirections")
    public BaseModel wayDirections(HttpServletRequest request, HttpServletResponse response
            , @RequestBody LatLngReq latLngReq) {
        BaseModel baseModel = new BaseModel();
        WayDirectionsResult wayDirectionsResult = mapService.wayDirections(latLngReq);
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("wayDirectionsResult",wayDirectionsResult);
        baseModel.setBody(result);
        return baseModel;
    }
}
