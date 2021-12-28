package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Location;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.LocationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceFacade implements LocationService{
    private final LocationRepository locationRepository;

    @Override
    public Location saveLocation(Location location) {
        log.info("Saving location");
        return locationRepository.save(location);
    }
}
