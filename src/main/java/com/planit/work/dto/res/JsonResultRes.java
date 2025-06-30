package com.planit.work.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "api 응답 dto")
@Getter
public class JsonResultRes {
    @Schema(description = "성공여부")
    private boolean success;

    @Schema(description = "코드")
    private String code;

    @Schema(description = "메시지")
    private String msg;

    @Schema(description = "데이터")
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
