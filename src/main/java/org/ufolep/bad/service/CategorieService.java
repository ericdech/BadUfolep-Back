package org.ufolep.bad.service;

import java.util.List;

import org.ufolep.bad.domain.Categorie;

public interface CategorieService {

	/**
	 * Retourne un catégorie à partir de son id
	 * @param idCategorie
	 * @return
	 * @throws Exception
	 */
	Categorie get(final Integer idCategorie);

	/**
	 * Enregistre une catégorie
	 * @param catégorie
	 * @throws Exception
	 */
	public Categorie save(final Categorie categorie);

	/**
	 * Supprime une catégorie
	 * @param id
	 * @throws Exception
	 */
	public void delete(final Integer id);

	/**
	 * Retourne toutes les catégories
	 * @return
	 */
	List<Categorie> getByChampionnat(final Integer IdChampionnat);
}