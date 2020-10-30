package org.ufolep.bad.repository;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.Adherent;

public interface AdherentRepository extends CrudRepository<Adherent, Integer> {

	/**
	 * Retourne un adhérent à partir de son pseudo
	 * @param pseudo
	 * @return
	 */
	Adherent findByPseudo(final String pseudo);
}