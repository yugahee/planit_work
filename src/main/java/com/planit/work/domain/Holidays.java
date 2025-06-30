package com.planit.work.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "holidays")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Holidays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private int idx;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "local_name", nullable = false)
    private String localName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "fixed")
    private String fixed;

    @Column(name = "global")
    private String global;

    @Column(name = "counties")
    private String counties;

    @Column(name = "launchYear")
    private int launchYear;

    @Column(name = "types")
    private String types;
}
