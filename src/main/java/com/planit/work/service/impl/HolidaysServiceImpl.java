package com.planit.work.service.impl;

import com.planit.work.domain.Country;
import com.planit.work.domain.Holidays;
import com.planit.work.dto.req.DeleteReq;
import com.planit.work.dto.req.RefreshReq;
import com.planit.work.dto.req.SearchReq;
import com.planit.work.dto.res.HolidaysRes;
import com.planit.work.repository.CountryRepository;
import com.planit.work.repository.HolidayRepository;
import com.planit.work.service.HolidaysSerivce;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidaysServiceImpl implements HolidaysSerivce {
    private static final Logger log = LoggerFactory.getLogger(HolidaysServiceImpl.class);

    @Autowired CountryRepository countryRepository;
    @Autowired HolidayRepository holidayRepository;

    // api 호출
    private final WebClient webClient;

    // 나라 api
    public List<Country> getCountryList() {
        return webClient.get()
                .uri("https://date.nager.at/api/v3/AvailableCountries")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Country>>() {})
                .block();
    }

    // 나라,연도별 공휴일 api
    public List<Holidays> getCountryHolidays(int year, String countryCode) {
        List<HolidaysRes> list = webClient.get()
                .uri("https://date.nager.at/api/v3/PublicHolidays/{year}/{countryCode}", year, countryCode)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<HolidaysRes>>() {})
                .block();

        return list.stream().map(dto -> {
            String typesAsString = (dto.getTypes() != null && !dto.getTypes().isEmpty()) ? dto.getTypes().get(0) : null;
            String countiesAsString = (dto.getCounties() != null && !dto.getCounties().isEmpty()) ? String.join(",", dto.getCounties()) : null;

            return new Holidays(
                    0,
                    dto.getCountryCode(),
                    dto.getLocalName(),
                    dto.getName(),
                    dto.getDate(),
                    dto.getFixed(),
                    dto.getGlobal(),
                    countiesAsString,
                    dto.getLaunchYear(),
                    typesAsString
            );
        }).toList();
    }


    @Override
    public boolean holidaysAllDataSave() {
        try {
            // 나라코드 리스트 가져오기
            List<Country> countryList = this.getCountryList();
            // 나라코드 저장
            countryRepository.saveAll(countryList);

            // 현재 연도
            LocalDate today = LocalDate.now();
            // 나라별로 공휴일 가져오기
            for(Country country : countryList){
                int year = today.getYear();

                for(int i = 0; i < 6; i++){
                    List<Holidays> HolidaysList = this.getCountryHolidays(year, country.getCountryCode());
                    holidayRepository.saveAll(HolidaysList); // 공휴일 저장
                    year--;
                }

            }

        }catch (Exception e){
            log.info("공휴일 데이터 적재 ERROR : " + e);
            return false;
        }

        return true;
    }

    @Override
    public Page<Holidays> selectHolidays(SearchReq req) {
        log.info("공휴일 검색 req : " + req);
        Page<Holidays> data = null;
        // 페이징 셋팅
        Pageable pageable = PageRequest.of(req.getCurrPage()-1, req.getRowPerPage());

        try {
            // 검색
            data = holidayRepository.findHolidaysBySearch(req.getCountryCode(), req.getYear(), req.getTypes(), pageable);
        }catch (Exception e){
            log.info("공휴일 검색 ERROR : " + e);
        }

        return data;
    }

    @Override
    public boolean holidaysRefresh(RefreshReq req) {
        try{
            // 기존 데이터 삭제
            holidayRepository.deleteByCountryCodeAndYear(req.getCountryCode(), req.getYear());

            // 최신 데이터 호출 및 저장
            List<Holidays> HolidaysList = this.getCountryHolidays(req.getYear(), req.getCountryCode());
            holidayRepository.saveAll(HolidaysList); // 저장
        }catch (Exception e){
            log.info("공휴일 재동기화 ERROR : " + e);
            return false;
        }

        return true;
    }

    @Override
    public boolean holidaysDelete(DeleteReq req) {
        try{
            // 데이터 삭제
            holidayRepository.deleteByCountryCodeAndYear(req.getCountryCode(), req.getYear());
        }catch (Exception e){
            log.info("공휴일 삭제 ERROR : " + e);
            return false;
        }

        return true;
    }

    @Override
    public boolean holidaysAutoRefresh() {
        try{
            int currentYear = LocalDate.now().getYear();
            int[] yearArr = {currentYear, currentYear-1};

            // 전년도, 금년도 데이터 삭제
            for(int i = 0; i < yearArr.length; i++){
                holidayRepository.deleteByYear(yearArr[i]);
            }
            
            // 나라 데이터 가져오기
            List<Country> countryList = countryRepository.findAll();

            for(Country country : countryList){
                for(int i = 0; i < yearArr.length; i++){
                    // 공휴일 데이터 가져오기
                    List<Holidays> HolidaysList = this.getCountryHolidays(yearArr[i], country.getCountryCode());
                    holidayRepository.saveAll(HolidaysList); // 공휴일 저장
                }
            }
        }catch (Exception e){
            log.info("공휴일 자동 재동기화 ERROR : " + e);
            return false;
        }

        return true;
    }


}
