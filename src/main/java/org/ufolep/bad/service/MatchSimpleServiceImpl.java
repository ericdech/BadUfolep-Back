package org.ufolep.bad.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.MatchSimple;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.MatchSimpleRepository;

@Service
class MatchSimpleServiceImpl implements MatchSimpleService {

	private MatchSimpleRepository matchSimpleRepository; 

	@Autowired
	public MatchSimpleServiceImpl(
		final MatchSimpleRepository matchSimpleRepository) {
		this.matchSimpleRepository = matchSimpleRepository;
	}

	@Override
	public MatchSimple get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche du match simple
		Optional<MatchSimple> matchSimple = matchSimpleRepository.findById(id);
		if (!matchSimple.isPresent()) {
			throw new NotFoundException("Aucun match simple pour l'idenfiant " + id + ".");
		}
		return matchSimple.get();
	}

	@Override
	public MatchSimple save(final MatchSimple matchSimple) {

		// Aucune match simple à enregistrer
		if (matchSimple == null) {
			throw new BadRequestException("Aucune match simple à enregistrer.");
		}

		// Enregistrement
		return matchSimpleRepository.save(matchSimple);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		matchSimpleRepository.deleteById(id);
	}
}