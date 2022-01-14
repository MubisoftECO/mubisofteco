package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;

import java.util.List;

public interface FlagService {
    Flag saveFlag(Flag flag);
    List<Flag> getAllFlags();
    Flag getFlag(Long id);
}
