package com.example.hackingproject.qna.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaData {

    // Question
    public Integer IDX;
    public String USER_ID;
    public String USER_NAME;
    public String Q_TITLE;
    public String Q_CONTENT;
    public String Q_CREATE_DT;

    // Answer
    public Integer IDX_CONNECTION;
    public String A_TITLE;
    public String A_CONTENT;
    public String A_CREATE_DT;
}
