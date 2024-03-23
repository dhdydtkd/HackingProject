$.ajaxGET = function (url, data= null, successCallBack = null, failCallBack = null, type = null) {
    let fullURL = "http://localhost:8080/"+url;
    let ajaxSuccessCallBack = null;
    let ajaxFailCallBack = null;
    let contentType = "application/json;charset=UTF-8";

    if(successCallBack != null && typeof successCallBack == 'function'){
        ajaxSuccessCallBack = successCallBack;
    }
    if(failCallBack != null && typeof failCallBack == 'function'){
        ajaxFailCallBack = failCallBack;
    }
    if(type != null && type == "file"){
        contentType = contentType; // 수정 필요
    }
    $.ajax({
        type: "GET",
        url: fullURL,
        data : JSON.stringify(data),
        contentType: contentType,
        success: (data) => {
            if(data.state.code=="0000"){
                ajaxSuccessCallBack(data);
            }else{
                ajaxFailCallBack(data);
            }
        },
        error:(data)=>{
            ajaxFailCallBack(data);
        }
    });
}

$.ajaxPOST = function (url, data= null, successCallBack = null, failCallBack = null, type = null) {
    let fullURL = "http://localhost:8080/"+url;
    let ajaxSuccessCallBack = null;
    let ajaxFailCallBack = null;
    let contentType = "application/json;charset=UTF-8";

    if(successCallBack != null && typeof successCallBack == 'function'){
        ajaxSuccessCallBack = successCallBack;
    }
    if(failCallBack != null && typeof failCallBack == 'function'){
        ajaxFailCallBack = failCallBack;
    }
    if(type != null && type == "file"){
        contentType = contentType; // 수정 필요
    }
    $.ajax({
        type: "POST",
        url: fullURL,
        data : JSON.stringify(data),
        contentType: contentType,
        success: (data) => {
            ajaxSuccessCallBack(data);
        },
        error:(data)=>{
            ajaxFailCallBack(data);
        }
    });
}

$.ajaxFormPOST = function (url, data= null, successCallBack = null, failCallBack = null, type = null) {
    let fullURL = "http://localhost:8080/"+url;
    let header = null;
    let ajaxSuccessCallBack = null;
    let ajaxFailCallBack = null;

    if(successCallBack != null && typeof successCallBack == 'function'){
        ajaxSuccessCallBack = successCallBack;
    }
    if(failCallBack != null && typeof failCallBack == 'function'){
        ajaxFailCallBack = failCallBack;
    }

    $.ajax({
        type: "POST",
        header : header,
        url: fullURL,
        data : data,
        processData : false,
        contentType: false,
        mimeType: "multipart/form-data",
        success: (data) => {
            ajaxSuccessCallBack(data);
        },
        error:(data)=>{
            ajaxFailCallBack(data);
        }
    });
}
