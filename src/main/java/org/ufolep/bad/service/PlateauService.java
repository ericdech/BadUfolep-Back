package org.ufolep.bad.service;

import java.util.List;

import org.ufolep.bad.domain.Plateau;

public interface PlateauService {

	/**
	 * Retourne un plateau à partir de son id
	 * @param idPlateau
	 * @return
	 * @throws Exception
	 */
	Plateau get(final Integer idPlateau);

	/**
	 * Enregistre un plateau
	 * @param plateau
	 * @throws Exception
	 */
	public Plateau save(final Plateau plateau);

	/**
	 * Supprime un plateau
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);

	/**
	 * Retourne les plateaux d'un championnat
	 * @param idChampionnat
	 * @return
	 * @throws Exception
	 */
	public List<Plateau> getByChampionnat(final Integer idChampionnat);
}