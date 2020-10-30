package org.ufolep.bad.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.Categorie;

public interface CategorieRepository
	extends CrudRepository<Categorie, Integer> {

	/**
	 * Retourne les catégorioes d'un championnat
	 * @param idChampionnat
	 * @return
	 */
	List<Categorie> findByIdChampionnat(final Integer idChampionnat);
}