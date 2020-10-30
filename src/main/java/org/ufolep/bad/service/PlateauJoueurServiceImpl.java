package org.ufolep.bad.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.PlateauJoueur;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.PlateauJoueurRepository;

@Service
class PlateauJoueurServiceImpl implements PlateauJoueurService {

	private PlateauJoueurRepository plateauJoueurRepository; 

	@Autowired
	public PlateauJoueurServiceImpl(
		final PlateauJoueurRepository plateauJoueurRepository) {
		this.plateauJoueurRepository = plateauJoueurRepository;
	}

	@Override
	public PlateauJoueur get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche de l'inscription du joueur
		Optional<PlateauJoueur> plateauJoueur = plateauJoueurRepository.findById(id);
		if (!plateauJoueur.isPresent()) {
			throw new NotFoundException("Aucune inscription pour l'idenfiant " + id + ".");
		}
		return plateauJoueur.get();
	}

	@Override
	public PlateauJoueur save(final PlateauJoueur plateauJoueur) {

		// Aucune inscription à enregistrer
		if (plateauJoueur == null) {
			throw new BadRequestException("Aucune inscription à enregistrer.");
		}

		// Enregistrement
		return plateauJoueurRepository.save(plateauJoueur);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		plateauJoueurRepository.deleteById(id);
	}

	@Override
	public PlateauJoueur get(final int idPlateau, final int idJoueur) {
		return plateauJoueurRepository.findByIdPlateauAndIdJoueur(idPlateau, idJoueur);
	}

	@Override
	public boolean saveInscription (final int idPlateau, final int idJoueur, final boolean inscription) {

		// Recherche d'une précédente inscription
		PlateauJoueur plateauJoueur = 
			plateauJoueurRepository.findByIdPlateauAndIdJoueur(idPlateau, idJoueur);

		// Enregistrement d'une nouvelle inscription
		if (inscription && plateauJoueur == null) {
			plateauJoueur = new PlateauJoueur();
			plateauJoueur.setIdPlateau(idPlateau);
			plateauJoueur.setIdJoueur(idJoueur);
			plateauJoueur = plateauJoueurRepository.save(plateauJoueur);

		// Desinscription
		} else if (!inscription && plateauJoueur != null) {
			plateauJoueurRepository.delete(plateauJoueur);
			plateauJoueur = null;
		}

		// Return
		return (plateauJoueur != null);
	}

	@Override
	public char savePresence(final int idPlateau, final int idJoueur, final Character presence) {

		// Recherche de l'inscription
		PlateauJoueur plateauJoueur = 
			plateauJoueurRepository.findByIdPlateauAndIdJoueur(idPlateau, idJoueur);
		if (plateauJoueur == null) {
			throw new BadRequestException("Le joueur n'est pas inscrit sur le plateau.");
		}

		// Enregistrement de la présence
		plateauJoueur.setPresence(presence);
		plateauJoueurRepository.save(plateauJoueur);

		// Return
		return plateauJoueur.getPresence();
	}
}