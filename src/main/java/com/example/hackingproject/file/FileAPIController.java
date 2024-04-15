package com.example.hackingproject.file;

import com.example.hackingproject.common.ServerStateEnum;
import com.example.hackingproject.file.dto.FileReq;
import com.example.hackingproject.file.vo.FileVO;
import com.example.hackingproject.BaseModel;
import com.example.hackingproject.file.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

@RestController
@RequestMapping("/file")
public class FileAPIController {

    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST, path = "/fileupload")
    public BaseModel fileUpload(HttpServletRequest request, HttpServletResponse response
            //,@RequestParam("body") String id
            , @RequestParam("files") MultipartFile[] multipartFiles
            ) {
        BaseModel baseModel = new BaseModel();
        Boolean result = null;
        result = fileService.uploadFile(multipartFiles);
        if(!result){
            baseModel.setState(ServerStateEnum.FAIL);
        }
        return baseModel;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/filelist")
    public BaseModel getFileList(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();
        List<FileVO> fileList = fileService.getFileList();
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("fileList",fileList);
        baseModel.setBody(result);
        return baseModel;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/filedelete")
    public BaseModel fileDelete(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Integer fileNo) {
        BaseModel baseModel = new BaseModel();
        Integer result = fileService.fileDelete(fileNo);

        if(result==1){//정상적인 삭제
            baseModel.setBody(null);
            return baseModel;
        }else if(result==0){//이미 삭제된 게시글
            baseModel.setBody(null);
            baseModel.setState(ServerStateEnum.FILE_DELETE_DUPLICATE);
            return baseModel;
        }else{//오류 발생
            baseModel.setBody(null);
            baseModel.setState(ServerStateEnum.FAIL);
            return baseModel;
        }
    }

    @RequestMapping(value="/filedownload")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response
            //, @RequestBody Integer fileNo) throws Exception {
            , @RequestParam("fileNo") Integer fileNo) throws Exception {
        FileReq file = fileService.fileFind(fileNo);

        File downloadFile = new File(file.getFileUrl());
        byte[] fileByte = FileUtils.readFileToByteArray(downloadFile);

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);

        //response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getFileName(),"UTF-8") +"\";");
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getFileName(), String.valueOf(StandardCharsets.UTF_8))+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
