package org.ufolep.bad.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.Club;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.ClubRepository;

@Service
class ClubServiceImpl implements ClubService {

	private final ClubRepository clubRepository;

	@Autowired
	public ClubServiceImpl(final ClubRepository clubRepository) {
		this.clubRepository = clubRepository;
	}

	@Override
	public Club get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche du club
		Optional<Club> club = clubRepository.findById(id);
		if (!club.isPresent()) {
			throw new NotFoundException("Aucun club pour l'idenfiant " + id + ".");
		}
		return club.get();
	}

	@Override
	public Club save(final Club club) {

		// Identifiant non renseigné
		if (club == null) {
			throw new BadRequestException("Aucun club à enregistrer.");
		}

		// Enregistrement
		return clubRepository.save(club);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		clubRepository.deleteById(id);
	}
}