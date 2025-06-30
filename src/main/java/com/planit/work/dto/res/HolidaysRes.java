package com.planit.work.dto.res;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class HolidaysRes {
    private Date date;
    private String localName;
    private String name;
    private String countryCode;
    private String fixed;
    private String global;
    private List<String> counties;
    private int launchYear;
    private List<String> types;
}
