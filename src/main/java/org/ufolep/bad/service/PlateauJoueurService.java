package org.ufolep.bad.service;

import org.ufolep.bad.domain.PlateauJoueur;

public interface PlateauJoueurService {

	/**
	 * Retourne un joueur ur un plateau à partir de son id
	 * @param idJoueurPlateau
	 * @return
	 * @throws Exception
	 */
	PlateauJoueur get(final Integer idJoueurPlateau);

	/**
	 * Enregistre un joueur sur un plateau 
	 * @param plateauJoueur
	 * @throws Exception
	 */
	public PlateauJoueur save(final PlateauJoueur plateauJoueur);

	/**
	 * Supprime un joueur sur un plateau 
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);

	/**
	 * Retourne l'inscription d'un joueur sur un plateau
	 * @param idPlateau
	 * @param idJoueur
	 * @return PlateauJoueur
	 */
	public PlateauJoueur get(final int idPlateau, final int idJoueur);

	/**
	 * Enregistre l'inscription (ou la desincription) d'un joueur sur un plateau
	 * @param idPlateau
	 * @param idJoueur
	 * @param inscription
	 * @return boolean
	 */
	public boolean saveInscription(final int idPlateau, final int idJoueur, final boolean inscription);

	/**
	 * Enregistre la présence d'un joueur sur un plateau
	 * @param idPlateau
	 * @param idJoueur
	 * @param presence
	 * @return char
	 */
	public char savePresence(final int idPlateau, final int idJoueur, final Character presence);
}