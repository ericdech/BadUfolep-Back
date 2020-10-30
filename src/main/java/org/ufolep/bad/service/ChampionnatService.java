package org.ufolep.bad.service;

import java.util.List;

import org.ufolep.bad.domain.Championnat;

public interface ChampionnatService {

	/**
	 * Retourne un Championnat à partir de son id
	 * @param idChampionnat
	 * @return
	 * @throws Exception
	 */
	Championnat get(final Integer idChampionnat);

	/**
	 * Enregistre un Championnat
	 * @param Championnat
	 * @throws Exception
	 */
	public Championnat save(final Championnat Championnat);

	/**
	 * Supprime un championnat
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);

	/**
	 * Retourne tous les championnats
	 * @return
	 */
	List<Championnat> getAll();

	/**
	 * Retourne tous les championnats auxquels un adhérent a participé en tant que joueur
	 * @return
	 */
	List<Championnat> getByAdherentAsJoueur(final Integer idAdherent);
}