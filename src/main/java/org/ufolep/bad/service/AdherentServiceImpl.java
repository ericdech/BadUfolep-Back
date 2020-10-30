package org.ufolep.bad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufolep.bad.domain.Adherent;
import org.ufolep.bad.domain.Championnat;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.exception.NotFoundException;
import org.ufolep.bad.repository.AdherentRepository;

@Service
class AdherentServiceImpl implements AdherentService {

	private AdherentRepository adherentRepository;
	private ChampionnatService championnatService;

	@Autowired
	public AdherentServiceImpl(
		final AdherentRepository adherentRepository,
		final ChampionnatService championnatService) {
		this.adherentRepository = adherentRepository;
		this.championnatService = championnatService;
	}

	@Override
	public Adherent get(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Recherche de l'adherent
		Optional<Adherent> adherent = adherentRepository.findById(id);
		if (!adherent.isPresent()) {
			throw new NotFoundException("Aucun adhérent pour l'idenfiant " + id + ".");
		}
		return adherent.get();
	}

	@Override
	public Adherent save(final Adherent adherent) {

		// Identifiant non renseigné
		if (adherent == null) {
			throw new BadRequestException("Aucun adhérent à enregistrer.");
		}

		// Enregistrement
		return adherentRepository.save(adherent);
	}

	@Override
	public void delete(final Integer id) {

		// Identifiant non renseigné
		if (id == null) {
			throw new BadRequestException("Identifiant non renseigné.");
		}

		// Suppression
		adherentRepository.deleteById(id);
	}

	@Override
	public Adherent connect(final String pseudo, final String password) {

		// Recherche de l'adhérent
		final Adherent adherent = adherentRepository.findByPseudo(pseudo);
		if (adherent == null) {
			throw new BadRequestException("Adhérent " + pseudo + " inconnu");
		}

		// Vérification du mot de passe
		if (password == null && adherent.getPassword() != null
				|| password != null && !password.equals(adherent.getPassword())) {
			throw new BadRequestException("Mot de passe incorrect");
		}

		// Renvoi de l'adhérent
		return adherent;
	}

	public List<Championnat> getChampionnats(final Integer idAdherent) {

		// Liste à retourner
		List<Championnat> championnats = null;

		// Recherche de l'adhérent
		Adherent adherent = get(idAdherent);
		
		// Si l'adhérent est administrateur, responsable de championnat ou responsable de club, retourne tous les championnats
		if (adherent.isAdministrateur()
			|| adherent.isResponsableChampionnat()
			|| adherent.isResponsableClub()) {
			championnats = championnatService.getAll();

		// Si l'adhérent est joueur, retourne tous les championnats auxquels il est inscrit
		} else {
			championnats = championnatService.getByAdherentAsJoueur(idAdherent);
		}

		// Retour
		return championnats;
	}
}