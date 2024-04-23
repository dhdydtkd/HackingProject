package com.example.hackingproject.qna.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QnaReq {
    public String title;
    public String content;

    public String create_nm;
    public String create_id;
}
