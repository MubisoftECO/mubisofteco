package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.AutonomousCommunityRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AutonomousCommunityServiceFacade implements AutonomousCommunityService
{

    private final AutonomousCommunityRepository acRepo;

    @Override
    public List<AutonomousCommunity> getAllAutonomousCommunities() {
        log.info("Fetching all the Autonomous Communities");
        return acRepo.findAll();
    }

    @Override
    public AutonomousCommunity getAutonomousCommunity(Long id) {
        log.info("Fetching community {}", id);
        return acRepo.findById(id).orElse(null);
    }

    @Override
    public AutonomousCommunity getAutonomousCommunity(String name) {
        log.info("Fetching community {}", name);
        return acRepo.getAutonomousCommunityByName(name);
    }
}
