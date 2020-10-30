package org.ufolep.bad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.Categorie;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.CategorieRepository;

@Service
class CategorieServiceImpl implements CategorieService {

	private CategorieRepository categorieRepository; 

	@Autowired
	public CategorieServiceImpl(
		final CategorieRepository categorieRepository) {
		this.categorieRepository = categorieRepository;
	}

	@Override
	public Categorie get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche de la categorie
		Optional<Categorie> categorie = categorieRepository.findById(id);
		if (!categorie.isPresent()) {
			throw new NotFoundException("Aucune catégorie pour l'idenfiant " + id + ".");
		}
		return categorie.get();
	}

	@Override
	public Categorie save(final Categorie categorie) {

		// Aucune catégorie à enregistrer
		if (categorie == null) {
			throw new BadRequestException("Aucune catégorie à enregistrer.");
		}

		// Enregistrement
		return categorieRepository.save(categorie);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		categorieRepository.deleteById(id);
	}

	public List<Categorie> getByChampionnat(final Integer idChampionnat) {

		// Identifiant de championnat non renseigné
		if (idChampionnat == null) {
			throw new BadRequestException("Identifiant de championnat non renseigné.");
		}

		// Recherche des catégories
		return categorieRepository.findByIdChampionnat(idChampionnat);
	}
}