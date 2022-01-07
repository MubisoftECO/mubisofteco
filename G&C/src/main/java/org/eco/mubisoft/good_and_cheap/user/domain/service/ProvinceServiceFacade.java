package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.AutonomousCommunityRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.ProvinceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProvinceServiceFacade implements ProvinceService
{

    private final ProvinceRepository provinceRepository;

    @Override
    public List<Province> getProvincesByAutonomousCommunity(AutonomousCommunity autonomousCommunity) {
        log.info("Getting provinces from {}", autonomousCommunity.getName());
        return provinceRepository.getProvincesByAutonomousCommunity(autonomousCommunity);
    }

    @Override
    public List<Province> getAllProvinces() {
        log.info("Getting all provinces.");
        return provinceRepository.findAll();
    }

    @Override
    public Province getProvince(Long id) {
        log.info("Getting Province {}", id);
        return provinceRepository.getById(id);
    }

    @Override
    public Province getProvince(String name) {
        log.info("Getting Province {}", name);
        return provinceRepository.getProvinceByName(name);
    }
}
