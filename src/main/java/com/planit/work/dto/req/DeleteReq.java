package com.planit.work.dto.req;

import lombok.Data;

@Data
public class DeleteReq {
    private int year;               // 검색연도
    private String countryCode;     // 검색 국가코드
}
