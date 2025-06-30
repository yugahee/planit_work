package com.planit.work.controller;

import com.planit.work.service.HolidaysSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@EnableScheduling
public class BatchController {
    private static final Logger log = LoggerFactory.getLogger(BatchController.class);

    @Autowired
    HolidaysSerivce holidaysSerivce;

    /*
    * 전년도·금년도 데이터를 자동 동기화
    * 실행기간 : 매년 1월 2일 01시
    * */
    @Scheduled(cron = "0 0 1 2 1 *")
    public void holidayBatch() {
        boolean result = holidaysSerivce.holidaysAutoRefresh();

        if(result){
            log.info("공휴일 자동 재동기화 완료");
        }else{
            log.info("공휴일 자동 재동기화 실패");
        }

    }
}
