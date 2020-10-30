package org.ufolep.bad.repository;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.PlateauJoueur;

public interface PlateauJoueurRepository
	extends CrudRepository<PlateauJoueur, Integer> {

	/**
	 * Retourne un joueur sur un plateau
	 * @param idPlateau
	 * @param idJoueur
	 * @return
	 */
	PlateauJoueur findByIdPlateauAndIdJoueur(final int idPlateau, final int idJoueur);
}