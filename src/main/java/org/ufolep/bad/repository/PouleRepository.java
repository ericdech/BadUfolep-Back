package org.ufolep.bad.repository;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.Poule;

public interface PouleRepository
	extends CrudRepository<Poule, Integer> {
}