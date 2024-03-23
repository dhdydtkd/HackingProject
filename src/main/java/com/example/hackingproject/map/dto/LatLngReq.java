package com.example.hackingproject.map.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatLngReq {
    public String startPointLat;
    public String startPointLng;
    public String endPointLat;
    public String endPointLng;
}
