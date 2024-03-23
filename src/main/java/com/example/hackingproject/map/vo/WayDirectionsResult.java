package com.example.hackingproject.map.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WayDirectionsResult {
    public Integer totalDistance;
    public Integer totalDuration;
    public List<LatLng> LatLngList;
}
