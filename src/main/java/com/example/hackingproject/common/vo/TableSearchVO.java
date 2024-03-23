package com.example.hackingproject.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableSearchVO {

    private Integer currentPage;
    private Integer pageMaxCount;
    private Integer totalPage;
    private Integer totalCount;

    private String title;
    private String id;
    private String searchStartTime;
    private String searchEndTime;

    public TableSearchVO(){

    }
    public TableSearchVO(TableSearchVO tableSearchVO, Integer searchTotalCount){
        currentPage = tableSearchVO.getCurrentPage();
        pageMaxCount = tableSearchVO.getPageMaxCount();
        totalCount = searchTotalCount;
        totalPage = searchTotalCount/this.pageMaxCount;
        if((searchTotalCount % this.pageMaxCount)>0){
            totalPage = totalPage+1;
        }
    }
}
