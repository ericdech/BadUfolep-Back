package org.ufolep.bad.service;

import org.ufolep.bad.domain.MatchSimple;

public interface MatchSimpleService {

	/**
	 * Retourne un match simple à partir de son id
	 * @param idMatchSimple
	 * @return
	 * @throws Exception
	 */
	MatchSimple get(final Integer idMatchSimple);

	/**
	 * Enregistre une match simple
	 * @param matchSimple
	 * @throws Exception
	 */
	public MatchSimple save(final MatchSimple matchSimple);

	/**
	 * Supprime une match simple
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);
}