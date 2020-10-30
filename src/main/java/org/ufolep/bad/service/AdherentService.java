package org.ufolep.bad.service;

import java.util.List;

import org.ufolep.bad.domain.Adherent;
import org.ufolep.bad.domain.Championnat;

public interface AdherentService {

	/**
	 * Retourne un adherent à partir de son id
	 * @param idAdherent
	 * @return
	 * @throws Exception
	 */
	Adherent get(final Integer idAdherent);

	/**
	 * Enregistre un adherent
	 * @param adherent
	 * @throws Exception
	 */
	public Adherent save(final Adherent adherent);

	/**
	 * Supprime un adhérent
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);

	/**
	 * Authentifie un adhérent partir de son pseudo et de son mot de passe
	 * @param idPlateau
	 * @param idJoueur
	 * @return Inscription
	 */
	Adherent connect(final String pseudo, final String password);

	/**
	 * Retourne les championnats accessibles à un adhérent donné
	 * @param idAdherent
	 * @return
	 * @throws Exception
	 */
	List<Championnat> getChampionnats(final Integer idAdherent);
}