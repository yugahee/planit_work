package com.planit.work.repository;

import com.planit.work.domain.Holidays;
import com.planit.work.dto.req.SearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HolidayRepository extends JpaRepository<Holidays, Integer> {
    @Query("SELECT h FROM Holidays h " +
            "WHERE h.countryCode = :countryCode " +
            "AND FUNCTION('YEAR', h.date) = :year " +
            "AND h.types LIKE CONCAT('%', :types, '%')")
    Page<Holidays> findHolidaysBySearch(@Param("countryCode") String countryCode,
                                        @Param("year") int year,
                                        @Param("types") String types, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Holidays h WHERE h.countryCode = :countryCode AND FUNCTION('YEAR', h.date) = :year")
    void deleteByCountryCodeAndYear(String countryCode, int year);

    @Modifying
    @Transactional
    @Query("DELETE FROM Holidays h WHERE FUNCTION('YEAR', h.date) = :year")
    void deleteByYear(int year);
}
