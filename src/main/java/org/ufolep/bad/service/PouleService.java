package org.ufolep.bad.service;

import org.ufolep.bad.domain.Poule;

public interface PouleService {

	/**
	 * Retourne une poule à partir de son id
	 * @param idPoule
	 * @return
	 * @throws Exception
	 */
	Poule get(final Integer idPoule);

	/**
	 * Enregistre une poule
	 * @param poule
	 * @throws Exception
	 */
	public Poule save(final Poule poule);

	/**
	 * Supprime une poule
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);
}