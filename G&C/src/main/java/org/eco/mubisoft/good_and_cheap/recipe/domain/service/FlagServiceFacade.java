package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;
import org.eco.mubisoft.good_and_cheap.recipe.domain.repo.FlagRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FlagServiceFacade implements FlagService {
    private final FlagRepository flagRepo;

    @Override
    public Flag saveFlag(Flag flag) {
        log.info("Saving flag {} on the database.", flag.getName());
        return flagRepo.save(flag);
    }

    @Override
    public List<Flag> getAllFlags(){return flagRepo.findAll();}
}
