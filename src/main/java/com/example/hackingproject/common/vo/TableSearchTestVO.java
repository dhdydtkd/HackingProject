package com.example.hackingproject.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableSearchTestVO {
	private String searchKeyword;
	private String title;
	}
    
    public TableSearchTestVO(){

    }
    
    public TableSearchTestVO(TableSearchTestVO tableSearchTestVO, String keyword){
        keyword = tableSearchTestVO.getSearchKeyword();
    	
    	currentPage = tableSearchTestVO.getCurrentPage();
        pageMaxCount = tableSearchTestVO.getPageMaxCount();
        System.out.println(keyword);
    }
}
