package com.example.hackingproject.notice.service;

import com.example.hackingproject.common.vo.TableSearchVO;
import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.notice.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NoticeService")
public class NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    public List<NoticeVO> getNoticeList(TableSearchVO tableSearchVO){
        if(tableSearchVO.getPageMaxCount()<=0){
            tableSearchVO.setPageMaxCount(10);
        }
        tableSearchVO.setCurrentPage((tableSearchVO.getCurrentPage()-1)*tableSearchVO.getPageMaxCount());
        return noticeDAO.getNoticeList(tableSearchVO);
    }

    public TableSearchVO getNoticeListCount(TableSearchVO tableSearchVO){
        if(tableSearchVO.getPageMaxCount()<=0){
            tableSearchVO.setPageMaxCount(10);
        }
        tableSearchVO.setCurrentPage((tableSearchVO.getCurrentPage()-1)*tableSearchVO.getPageMaxCount());
        Integer searchTotalCount = noticeDAO.getNoticeListCount(tableSearchVO);

        TableSearchVO tableSearch = new TableSearchVO(tableSearchVO, searchTotalCount);

        return tableSearch;
    }

    public Integer deleteNotice(Integer noticeNo){
        return noticeDAO.deleteNotice(noticeNo);
    }



}
