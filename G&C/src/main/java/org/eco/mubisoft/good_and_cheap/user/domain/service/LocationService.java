package org.eco.mubisoft.good_and_cheap.user.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.City;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Location;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;

import java.util.List;

public interface LocationService {
    Location saveLocation(Location location);
}
