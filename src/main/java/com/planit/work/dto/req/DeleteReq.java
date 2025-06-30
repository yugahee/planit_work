package com.planit.work.dto.req;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "삭제 요청 dto")
@Data
public class DeleteReq {
    @Parameter(description = "연도", example = "2025")
    private int year;
    @Parameter(description = "국가코드", example = "AD")
    private String countryCode;
}
