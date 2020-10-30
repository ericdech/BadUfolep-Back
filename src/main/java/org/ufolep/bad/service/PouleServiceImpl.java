package org.ufolep.bad.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.Poule;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.PouleRepository;

@Service
class PouleServiceImpl implements PouleService {

	private PouleRepository pouleRepository; 

	@Autowired
	public PouleServiceImpl(
		final PouleRepository pouleRepository) {
		this.pouleRepository = pouleRepository;
	}

	@Override
	public Poule get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche de la poule
		Optional<Poule> poule = pouleRepository.findById(id);
		if (!poule.isPresent()) {
			throw new NotFoundException("Aucune poule pour l'idenfiant " + id + ".");
		}
		return poule.get();
	}

	@Override
	public Poule save(final Poule poule) {

		// Aucune poule à enregistrer
		if (poule == null) {
			throw new BadRequestException("Aucune poule à enregistrer.");
		}

		// Enregistrement
		return pouleRepository.save(poule);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		pouleRepository.deleteById(id);
	}
}