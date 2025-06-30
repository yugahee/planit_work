package com.planit.work.repository;

import com.planit.work.domain.Country;
import com.planit.work.dto.res.CountriesRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

}
