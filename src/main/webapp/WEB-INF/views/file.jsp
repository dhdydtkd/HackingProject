<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>

<html>
<head>
    <title>Reve Spac Site</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="/css/main.css" />
</head>
<!-- Scripts -->
<script src="/js/jquery.min.js"></script>
<script src="/js/browser.min.js"></script>
<script src="/js/breakpoints.min.js"></script>
<script src="/js/util.js"></script>
<script src="/js/main.js"></script>
<script src="/js/common.js"></script>

<script src="/js/rsa/jsbn.js"></script>
<script src="/js/rsa/prng4.js"></script>
<script src="/js/rsa/rng.js"></script>
<script src="/js/rsa/rsa.js"></script>

<body class="is-preload">
<script>
    let uploadReadyFileDataList = [];
    let uploadReadyFileList = [];
    let uploadListMax = 5;
    let uploadListMaxSize = 500000000;

    let extensionList = ["pdf","zip","jpg","jpeg","png","tar","7z","txt","docx","doc","hwp","ppt","pptx","dmg"];
    $(() => {
        getFileList();

        $("#upload").click(function() {
            console.log(uploadReadyFileDataList);
            if(uploadReadyFileDataList.length<1){
                alert("업로드할 파일을 선택해주세요.");
                return;
            }
            let uploadMaxSize = 0;
            for (let i = 0; i < uploadReadyFileDataList.length; i++) { // 추가된 파일
                uploadMaxSize+=uploadReadyFileDataList[i].size;
            }
            if(uploadListMaxSize<uploadMaxSize){
                alert("업로드할 수 있는 총용량은 "+uploadListMaxSize/1000000+"MB 입니다.");
                return;
            }
            let formData = new FormData();
            let data = {
                createId : "sys"
            }
            //formData.append('body',JSON.stringify(data));
            for (let i = 0; i < uploadReadyFileDataList.length; i++) { // 추가된 파일
                formData.append('files', uploadReadyFileDataList[i]);
            }
            $.ajaxFormPOST("file/fileupload", formData, function(result){
                result = JSON.parse(result); // JSON.parse를 쓰는 이유?????......
                if (result.state.code == "0000") {
                    uploadReadyClear();
                    getFileList();
                }
            });
        });

        $("#cancel").click(function() {
            uploadReadyClear();
        });

    });
    function getFileList(){
        $.ajaxPOST("file/filelist", null, function(result){
            if (result.state.code == "0000") {
                let fileList = result.body.fileList;
                let fileBodyHTML = "";
                for(let i = 0 ; i<fileList.length ; i++){
                    fileBodyHTML += "<tr>";
                    fileBodyHTML += "<td>"+(+i+1)+"</td>";
                    fileBodyHTML += "<td style='display: inline-block;'>" +
                        "<a style='cursor: pointer; width: auto'" +'href="/file/filedownload?fileNo='+fileList[i].no+'"' +
                        ">"+fileList[i].fileName+"</a></td>";
                    if(fileList[i].fileSize/1000000>1.0){
                        fileBodyHTML += "<td>"+(fileList[i].fileSize/1000000.0).toFixed(3)+"MB</td>";
                    }else if(fileList[i].fileSize/1000>1.0){
                        fileBodyHTML += "<td>"+(fileList[i].fileSize/1000.0).toFixed(3)+"KB</td>";
                    }else{
                        fileBodyHTML += "<td>"+fileList[i].fileSize+"byte</td>";
                    }
                    fileBodyHTML += "<td>"+fileList[i].createDt+"</td>";
                    fileBodyHTML += "<td> <a type='button' name='file_delete' id='"+fileList[i].no+"' class='button small'>삭제</a></td>";
                    fileBodyHTML += "</tr>";
                }
                $('#table_body').empty();
                $('#table_body').append(fileBodyHTML);
                $(document).off('click',"a[name='file_delete']").on("click","a[name='file_delete']",function(){
                    $.ajaxPOST("file/filedelete", parseInt(this.id), function(result){
                        if(result.state.desc!=null){
                            alert(result.state.desc);
                        }else{
                            getFileList();
                        }
                    });
                });
                $(document).off('click',"p[name='file_download']").on("click","p[name='file_download']",function(){
                    console.log(parseInt(this.id));
                    $.ajaxPOST("file/filedownload", parseInt(this.id), function(result){
                        console.log(result);
                    });
                });


            }
        });
    }
    function uploadReadyClear() {
        uploadReadyFileList = [];
        uploadReadyFileDataList = [];
        drawUploadReadyFileList();
        $(".upload-name").val("첨부파일");
    }
    function fileSearch(param){
        if(uploadListMax<=uploadReadyFileList.length){
            alert("한번에 업로드 할 수 있는 파일 갯수는 "+uploadListMax+"개 입니다.");
            return;
        }
        let selectFileData = {
            name: param.files[0].name,
            size: param.files[0].size,
            lastModified : param.files[0].lastModified
        }
        let lastIndex = param.files[0].name.split('.').length;
        let extension = param.files[0].name.split('.')[lastIndex-1];
        let extensionPassFlag = false;
        for(let i = 0 ; extensionList.length > i ; i++){
            if(extension==extensionList[i]){
                extensionPassFlag = true;
            }
        }
        if(!extensionPassFlag){
            alert("업로드 할 수 있는 확장자 리스트를 아래와 같습니다.\n" +
                "pdf, jpg, jpeg, png, zip, tar, 7z, txt, docx, doc, hwp, ppt, pptx, dmg");
            return;
        }
        for(let i = 0 ; uploadReadyFileList.length > i ; i++){
            if(selectFileData.name == uploadReadyFileList[i].name
                &&selectFileData.lastModified == uploadReadyFileList[i].lastModified){
                alert("이미 등록된 파일입니다.");
                return;
            }
            else if(selectFileData.name == uploadReadyFileList[i].name){
                alert("같은 이름의 파일존재합니다.");
                return;
            }
        }
        $(".upload-name").val(param.files[0].name);
        uploadReadyFileList.push(selectFileData);
        uploadReadyFileDataList.push(param.files[0]);
        drawUploadReadyFileList();
    }
    function uploadReadyFileDelete(param){
        let uploadReadyFileDeleteFileName = param.id;
        for(let i = 0 ; uploadReadyFileList.length > i ; i++){
            if(uploadReadyFileDeleteFileName == uploadReadyFileList[i].name){
                uploadReadyFileList.splice(i, 1);
                uploadReadyFileDataList.splice(i, 1);
                i--;
                break;
            }
        }
        drawUploadReadyFileList();
    }
    function drawUploadReadyFileList(){
        $("#select_upload_file_list").empty();
        let html = "";
        for(let i = 0 ; uploadReadyFileList.length > i ; i++){
            let tempName = uploadReadyFileList[i].name;
            html+="<p>"+(i+1)+". "+tempName;
            html+="<input style='line-height:0px;padding:0px;height:20px;width:25px; margin-left: 10px' type='button' id='"+tempName+"' onclick='uploadReadyFileDelete(this)' value='X'/>";
            html+="</p>";
        }
        $("#select_upload_file_list").append(html);
    }
</script>
<!-- Wrapper -->
<div id="wrapper">
    <!-- Main -->
    <div id="main">
        <div class="inner">
            <!-- Header -->
            <header id="header">
                <strong>첨부파일</strong>
            </header>
            <div style="margin-top: 30px" class="filebox" >
                Upload 파일 선택 :
                <input style="width: 200px" class="upload-name" value="첨부파일" placeholder="첨부파일" readonly>
                <label for="file">파일찾기</label>
                <input type="file" id="file" onchange="fileSearch(this)">
            </div>
            <strong style="margin-top: 30px" >선택된 파일 리스트</strong>
            <div id="select_upload_file_list" class="col-12">
            </div>
            <div style="margin-top: 20px" class="col-12">
                <ul class="actions">
                    <li><input type="button" id="upload" value="Upload"/></li>
                    <li><input type="button" id="cancel" value="cancel"/></li>
                </ul>
            </div>
            <strong>Upload된 파일 리스트</strong>
            <div class="table-wrapper" style="margin-top: 20px">
                <table>
                    <thead>
                    <tr>
                        <th width="10%">번호</th>
                        <th width="30%">파일이름</th>
                        <th width="30%">파일사이즈</th>
                        <th width="20%">생성시간</th>
                        <th width="10%"></th>
                    </tr>
                    </thead>
                    <tbody id="table_body">

                    </tbody>
                </table>
            </div>

        </div>
    </div>
    <!-- Sidebar -->
    <jsp:include page="sidebar.jsp" flush="false"/>
</div>
<style>
    .filebox .upload-name {
        display: inline-block;
        height: 30px;
        padding: 0 10px;
        vertical-align: middle;
        border: 1px solid #dddddd;
        width: 78%;
        color: #999999;
    }
    .filebox label {
        display: inline-block;
        padding: 5px 15px;
        color: #fff;
        vertical-align: middle;
        background-color: #999999;
        cursor: pointer;
        height: 30px;
        margin-left: 10px;
    }
    .filebox input[type="file"] {
        position: absolute;
        width: 0;
        height: 0;
        padding: 0;
        overflow: hidden;
        border: 0;
    }
</style>
</body>
</html>