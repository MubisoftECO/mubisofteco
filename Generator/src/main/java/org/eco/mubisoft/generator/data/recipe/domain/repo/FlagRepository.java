package org.eco.mubisoft.generator.data.recipe.domain.repo;

import org.eco.mubisoft.generator.data.recipe.domain.model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagRepository extends JpaRepository<Flag, Long> {
}
