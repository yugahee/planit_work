package com.planit.work.controller;

import com.planit.work.domain.Holidays;
import com.planit.work.dto.req.DeleteReq;
import com.planit.work.dto.req.RefreshReq;
import com.planit.work.dto.req.SearchReq;
import com.planit.work.dto.res.JsonResultRes;
import com.planit.work.service.HolidaysSerivce;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/holidays")
@Tag(name = "Holidays", description = "나라 공휴일 관련 API")
public class HolidaysController {
    @Autowired
    HolidaysSerivce holidaysSerivce;

    @PostMapping("/allSave")
    @Operation(summary = "공휴일 데이터 적재", description = "최근 5년 나라별 공휴일을 일괄 저장합니다.")
    public Object holidaysAllSave(){
        boolean result = holidaysSerivce.holidaysAllDataSave();
        JsonResultRes res = new JsonResultRes(result, null);
        return res;
    }

    @GetMapping("/select")
    @Operation(summary = "검색", description = "연도별·국가별 필터 기반 공휴일 조회")
    public Object selectHolidays(@ParameterObject SearchReq req){
        JsonResultRes res = new JsonResultRes(false, null);

        try{
            Page<Holidays> holidaysList = holidaysSerivce.selectHolidays(req);
            res = new JsonResultRes(true, holidaysList);
        }catch (Exception e){
            res = new JsonResultRes(false, null);
        }


        return res;
    }

    @PostMapping("/refresh")
    @Operation(summary = "재동기화", description = "특정 연도·국가 데이터를 재호출하여 덮어쓰기")
    public Object holidaysRefresh(@ParameterObject RefreshReq req){
        boolean result = holidaysSerivce.holidaysRefresh(req);
        JsonResultRes res = new JsonResultRes(result, null);
        return res;
    }


    @PostMapping("/delete")
    @Operation(summary = "삭제", description = "특정 연도·국가의 공휴일 레코드 전체 삭제")
    public Object holidaysDelete(@ParameterObject DeleteReq req){
        boolean result = holidaysSerivce.holidaysDelete(req);
        JsonResultRes res = new JsonResultRes(result, null);
        return res;
    }
}
