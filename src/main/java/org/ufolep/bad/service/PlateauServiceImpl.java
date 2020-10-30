package org.ufolep.bad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.Plateau;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.PlateauRepository;

@Service
class PlateauServiceImpl implements PlateauService {

	// Repositories
	private PlateauRepository plateauRepository; 

	@Autowired
	public PlateauServiceImpl(
		final PlateauRepository plateauRepository) {
			this.plateauRepository = plateauRepository;
		}

	@Override
	public Plateau get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche du plateau
		Optional<Plateau> plateau = plateauRepository.findById(id);
		if (!plateau.isPresent()) {
			throw new NotFoundException("Aucun plateau pour l'idenfiant " + id + ".");
		}
		return plateau.get();
	}

	@Override
	public Plateau save(final Plateau plateau) {

		// Aucun plateau à enregistrer
		if (plateau == null) {
			throw new BadRequestException("Aucun plateau à enregistrer.");
		}

		// Enregistrement
		return plateauRepository.save(plateau);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		plateauRepository.deleteById(id);
	}

	@Override
	public List<Plateau> getByChampionnat(final Integer idChampionnat) {

		// Identifiant de championnat non renseigné
		if (idChampionnat == null) {
			throw new BadRequestException("Identifiant de championnat non renseigné.");
		}

		// Recherche des plateaux
		return plateauRepository.findByIdChampionnat(idChampionnat);
	}
}