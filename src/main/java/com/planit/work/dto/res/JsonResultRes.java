package com.planit.work.dto.res;

import lombok.Getter;

@Getter
public class JsonResultRes {
    private boolean success;
    private String code;
    private String msg;
    private Object data;


    public JsonResultRes(boolean success, Object data) {
        this.success = success;
        if(success){
            this.code = "200";
            this.msg = "성공했습니다.";
        }else{
            this.code = "500";
            this.msg = "실패했습니다.";
        }

        this.data = data;
    }
}
