package com.planit.work.dto.req;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "재동기화 요청 dto")
@Data
public class SearchReq {
    @Parameter(description = "현재 페이지 번호 (1부터 시작)", example = "1")
    private int currPage = 1;
    @Parameter(description = "페이지당 row 수", example = "10")
    private int rowPerPage = 10;
    @Parameter(description = "연도", example = "2025")
    private int year;
    @Parameter(description = "국가코드", example = "AD")
    private String countryCode;
    @Parameter(description = "타입", example = "Public")
    private String types;
}
