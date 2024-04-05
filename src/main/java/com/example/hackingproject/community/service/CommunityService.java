package com.example.hackingproject.community.service;

import com.example.hackingproject.community.vo.CommunityVO;
import com.example.hackingproject.dao.CommunityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CommunityService")
public class CommunityService {

    @Autowired
    CommunityDAO communityDAO;

    public List<CommunityVO> getCommunityList(){
        List<CommunityVO> communityList = communityDAO.getCommunityList();
        return communityList;
    }
}
