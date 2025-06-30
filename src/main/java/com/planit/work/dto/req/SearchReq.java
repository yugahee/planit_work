package com.planit.work.dto.req;

import lombok.Data;

@Data
public class SearchReq {
    private int currPage = 1;       // 현재페이지
    private int rowPerPage = 10;    // 페이지당 row 수
    private int year;               // 검색연도
    private String countryCode;     // 검색 국가코드
    private String types;           // 타입
}
