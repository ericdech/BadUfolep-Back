package org.ufolep.bad.service;

import java.util.List;

import org.ufolep.bad.domain.Joueur;

public interface JoueurService {

	/**
	 * Retourne un joueur à partir de son id
	 * @param idJoueur
	 * @return
	 * @throws Exception
	 */
	Joueur get(final Integer idJoueur);

	/**
	 * Enregistre un joueur
	 * @param joueur
	 * @throws Exception
	 */
	public Joueur save(final Joueur joueur);

	/**
	 * Supprime un joueur
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);

	/**
	 * Retourne les références de joueur d'un adhérent
	 * @param idAdherent
	 * @return
	 */
	List<Joueur> getByIdAdherent(final Integer idAdherent);	
}