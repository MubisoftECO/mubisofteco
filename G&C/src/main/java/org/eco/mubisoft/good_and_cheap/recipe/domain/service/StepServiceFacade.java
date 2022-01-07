package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Step;
import org.eco.mubisoft.good_and_cheap.recipe.domain.repo.StepRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StepServiceFacade implements StepService{
    private final StepRepository stepRepo;

    @Override
    public List<Step> getStepsByRecipe(Recipe recipe){return stepRepo.getStepsByRecipe(recipe);}
}
