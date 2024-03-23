package com.example.hackingproject;

import com.example.hackingproject.common.ServerStateEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseModel {

    private ServerStateEnum State;
    private Object body;

    public BaseModel(){
        this.State = ServerStateEnum.SUCCESS;
    }

}
