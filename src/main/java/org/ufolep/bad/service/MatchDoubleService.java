package org.ufolep.bad.service;

import org.ufolep.bad.domain.MatchDouble;

public interface MatchDoubleService {

	/**
	 * Retourne un  match double à partir de son id
	 * @param idMatchDouble
	 * @return
	 * @throws Exception
	 */
	MatchDouble get(final Integer idMatchDouble);

	/**
	 * Enregistre un match double
	 * @param matchDouble
	 * @throws Exception
	 */
	public MatchDouble save(final MatchDouble matchDouble);

	/**
	 * Supprime une match double
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);
}