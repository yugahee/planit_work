package com.planit.work;

import com.planit.work.service.HolidaysSerivce;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlanitWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanitWorkApplication.class, args);
	}

	// 초기 실행 로직 정의
	@Bean
	public ApplicationRunner initHoliday(HolidaysSerivce holidaysService) {
		return args -> {
			try {
				holidaysService.holidaysAllDataSave();
				System.out.println("공휴일 초기 데이터 가져오기 성공!");
			} catch (Exception e) {
				System.err.println("공휴일 초기 데이터 ERROR : " + e.getMessage());
			}
		};
	}

}
