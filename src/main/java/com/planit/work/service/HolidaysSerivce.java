package com.planit.work.service;

import com.planit.work.domain.Holidays;
import com.planit.work.dto.req.DeleteReq;
import com.planit.work.dto.req.RefreshReq;
import com.planit.work.dto.req.SearchReq;
import org.springframework.data.domain.Page;

public interface HolidaysSerivce {
    boolean holidaysAllDataSave(); // 공휴일 데이터 적재

    Page<Holidays> selectHolidays(SearchReq req); // 공휴일 검색

    boolean holidaysRefresh(RefreshReq req); // 공휴일 재동기화

    boolean holidaysDelete(DeleteReq req); // 나라

    boolean holidaysAutoRefresh();
}
