package com.example.hackingproject.notice;


import com.example.hackingproject.BaseModel;
import com.example.hackingproject.common.ServerStateEnum;
import com.example.hackingproject.common.vo.TableSearchVO;
import com.example.hackingproject.notice.service.NoticeService;
import com.example.hackingproject.notice.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeAPIController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(method = RequestMethod.POST, path = "/noticelist")
    public BaseModel getNoticeList(HttpServletRequest request, HttpServletResponse response
            , @RequestBody TableSearchVO tableSearchVO) {
        BaseModel baseModel = new BaseModel();
        List<NoticeVO> noticeList = noticeService.getNoticeList(tableSearchVO);
        TableSearchVO searchVO = noticeService.getNoticeListCount(tableSearchVO);
        Map<String, Object> result = new HashMap<String,Object>();

        result.put("noticeList",noticeList);
        result.put("searchData",searchVO);
        baseModel.setBody(result);
        return baseModel;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/noticedelete")
    public BaseModel deleteNotice(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Integer noticeNo) {
        BaseModel baseModel = new BaseModel();
        Integer result = noticeService.deleteNotice(noticeNo);
        if(result==1){//정상적인 삭제
            baseModel.setBody(null);
            return baseModel;
        }else if(result==0){//이미 삭제된 게시글
            baseModel.setBody(null);
            baseModel.setState(ServerStateEnum.NOTICE_DELETE_DUPLICATE);
            return baseModel;
        }else{//오류 발생
            baseModel.setBody(null);
            baseModel.setState(ServerStateEnum.FAIL);
            return baseModel;
        }
    }
}
