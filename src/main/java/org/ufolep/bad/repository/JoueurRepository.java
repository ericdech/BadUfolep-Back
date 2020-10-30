package org.ufolep.bad.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.ufolep.bad.domain.Joueur;

public interface JoueurRepository
	extends CrudRepository<Joueur, Integer> {

	/**
	 * Retourne les joueurs correpondant à un adhérent	 * @param idAdherent
	 * @return
	 */
	List<Joueur> findByIdAdherent(final Integer idAdherent);
}