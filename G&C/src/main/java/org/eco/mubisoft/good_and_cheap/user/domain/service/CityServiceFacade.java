package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.good_and_cheap.user.domain.model.City;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.CityRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.ProvinceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceFacade implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> getCitiesByProvince(Province province) {
        log.info("Getting cities from {}", province.getName());
        return cityRepository.getCitiesByProvince(province);
    }

    @Override
    public City getCity(Long id) {
        log.info("Getting city {}", id);
        return cityRepository.getById(id);
    }

    @Override
    public City getCity(String name) {
        log.info("Getting city {}", name);
        return cityRepository.getCityByName(name);
    }
}
