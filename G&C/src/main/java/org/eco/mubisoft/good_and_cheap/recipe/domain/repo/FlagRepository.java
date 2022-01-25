package org.eco.mubisoft.good_and_cheap.recipe.domain.repo;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlagRepository extends JpaRepository<Flag, Long> {
}
