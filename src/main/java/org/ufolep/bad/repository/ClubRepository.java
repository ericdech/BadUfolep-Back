package org.ufolep.bad.repository;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.Club;

public interface ClubRepository
	extends CrudRepository<Club, Integer> {
}