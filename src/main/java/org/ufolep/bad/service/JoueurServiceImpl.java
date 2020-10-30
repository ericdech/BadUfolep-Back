package org.ufolep.bad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.Joueur;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.JoueurRepository;

@Service
class JoueurServiceImpl implements JoueurService {

	private JoueurRepository joueurRepository; 

	@Autowired
	public JoueurServiceImpl(
		final JoueurRepository joueurRepository) {
		this.joueurRepository = joueurRepository;
	}

	@Override
	public Joueur get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche du joueur
		Optional<Joueur> joueur = joueurRepository.findById(id);
		if (!joueur.isPresent()) {
			throw new NotFoundException("Aucun joueur pour l'idenfiant " + id + ".");
		}
		return joueur.get();
	}

	@Override
	public Joueur save(final Joueur joueur) {

		// Aucun joueur à enregistrer
		if (joueur == null) {
			throw new BadRequestException("Aucun joueur à enregistrer.");
		}

		// Enregistrement
		return joueurRepository.save(joueur);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		joueurRepository.deleteById(id);
	}

	@Override
	public List<Joueur> getByIdAdherent(final Integer idAdherent) {
		return joueurRepository.findByIdAdherent(idAdherent);
	}
}