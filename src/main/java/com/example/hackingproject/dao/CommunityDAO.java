package com.example.hackingproject.dao;


import com.example.hackingproject.community.vo.CommunityVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityDAO {

    List<CommunityVO> getCommunityList();
}
