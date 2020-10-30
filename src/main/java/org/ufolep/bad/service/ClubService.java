package org.ufolep.bad.service;

import org.ufolep.bad.domain.Club;

public interface ClubService {

	/**
	 * Retourne un club à partir de son id
	 * @param idClub
	 * @return
	 * @throws Exception
	 */
	Club get(final Integer idClub);

	/**
	 * Enregistre un club
	 * @param club
	 * @throws Exception
	 */
	public Club save(final Club club);

	/**
	 * Supprime un club
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);
}