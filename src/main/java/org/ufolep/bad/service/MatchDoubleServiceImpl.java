package org.ufolep.bad.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.MatchDouble;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.MatchDoubleRepository;

@Service
class MatchDoubleServiceImpl implements MatchDoubleService {

	private MatchDoubleRepository matchDoubleRepository; 

	@Autowired
	public MatchDoubleServiceImpl(
		final MatchDoubleRepository matchDoubleRepository) {
		this.matchDoubleRepository = matchDoubleRepository;
	}

	@Override
	public MatchDouble get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche du match double
		Optional<MatchDouble> matchDouble = matchDoubleRepository.findById(id);
		if (!matchDouble.isPresent()) {
			throw new NotFoundException("Aucun match double pour l'idenfiant " + id + ".");
		}
		return matchDouble.get();
	}

	@Override
	public MatchDouble save(final MatchDouble matchDouble) {

		// Aucune match double à enregistrer
		if (matchDouble == null) {
			throw new BadRequestException("Aucune match double à enregistrer.");
		}

		// Enregistrement
		return matchDoubleRepository.save(matchDouble);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		matchDoubleRepository.deleteById(id);
	}
}