package com.example.hackingproject.dao;

import com.example.hackingproject.common.vo.TableSearchVO;
import com.example.hackingproject.notice.dto.NoticeReq;
import com.example.hackingproject.notice.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDAO {

    List<NoticeVO> getNoticeList(TableSearchVO tableSearchVO);
    Integer getNoticeListCount(TableSearchVO tableSearchVO);

    Integer addSchedulerNotice(NoticeReq notice);

    Integer getAddNoticeIndex();

    Integer deleteNotice(Integer noticeNo);

}
