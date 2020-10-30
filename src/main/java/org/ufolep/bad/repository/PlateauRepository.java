package org.ufolep.bad.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.Plateau;

public interface PlateauRepository
	extends CrudRepository<Plateau, Integer> {

	/**
	 * Retourne les plateaux d'un championnat
	 * @param idChampionnat
	 * @return
	 */
	List<Plateau> findByIdChampionnat(final Integer idChampionnat);
}