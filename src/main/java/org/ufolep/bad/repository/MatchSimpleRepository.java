package org.ufolep.bad.repository;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.MatchSimple;

public interface MatchSimpleRepository
	extends CrudRepository<MatchSimple, Integer> {
}