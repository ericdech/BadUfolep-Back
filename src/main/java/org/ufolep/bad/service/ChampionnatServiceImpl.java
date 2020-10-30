package org.ufolep.bad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.Championnat;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.ChampionnatRepository;

@Service
class ChampionnatServiceImpl implements ChampionnatService {

	// Repositores
	private ChampionnatRepository championnatRepository; 

	@Autowired
	public ChampionnatServiceImpl(
		final ChampionnatRepository championnatRepository) {
		this.championnatRepository = championnatRepository;
	}

	@Override
	public Championnat get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche de l'championnat
		Optional<Championnat> championnat = championnatRepository.findById(id);
		if (!championnat.isPresent()) {
			throw new NotFoundException("Aucun championnat pour l'idenfiant " + id + ".");
		}
		return championnat.get();
	}

	@Override
	public Championnat save(final Championnat championnat) {

		// Aucun chmapionnat à enregistrer
		if (championnat == null) {
			throw new BadRequestException("Aucun chmapionnat à enregistrer.");
		}

		// Enregistrement
		return championnatRepository.save(championnat);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		championnatRepository.deleteById(id);
	}

	public List<Championnat> getAll() {
		List<Championnat> championnatList = new ArrayList<>();
		championnatRepository.findAll().iterator().forEachRemaining(championnatList::add);
		return championnatList;
	}

	public List<Championnat> getByAdherentAsJoueur(final Integer idAdherent) {
		return championnatRepository.findChampionnatByIdAdherent(idAdherent);
	}
}