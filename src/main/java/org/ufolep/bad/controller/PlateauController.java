package  org.ufolep.bad.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ufolep.bad.domain.Plateau;
import org.ufolep.bad.domain.PlateauJoueur;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.InscriptionDto;
import org.ufolep.bad.dto.PlateauDto;
import org.ufolep.bad.dto.PresenceDto;
import org.ufolep.bad.exception.BadRequestException;
import org.ufolep.bad.service.PlateauJoueurService;
import org.ufolep.bad.service.PlateauService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Plateaux", description="Plateaux organisés dans les championnats")
@ApiResponses(value = {
	@ApiResponse(
		responseCode = "200", 
		description = "OK"),
	@ApiResponse(
		responseCode = "400", 
		description = "Bad request", 
		content = @Content(schema = @Schema(implementation = ErrorDto.class))),
	@ApiResponse(
		responseCode = "404", 
		description = "Not found", 
		content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
@RestController
@RequestMapping("/plateaux")
public class PlateauController {

	// Services
	private final PlateauService plateauService;
	private final PlateauJoueurService plateauJoueurService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public PlateauController(
		final PlateauService plateauService,
		final PlateauJoueurService plateauJoueurService,
		final ModelMapper modelMapper) {
		this.plateauService = plateauService;
		this.plateauJoueurService = plateauJoueurService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<Plateau> plateauFromPlateauDtoProvider = new Provider<Plateau>() {
			public Plateau get(ProvisionRequest<Plateau> request) {
				return new Plateau(((PlateauDto) request.getSource()).getIdPlateau());
			}
		};
		TypeMap<PlateauDto, Plateau> plateauFromPlateauDtoMap = 
			this.modelMapper.createTypeMap(PlateauDto.class, Plateau.class);
		plateauFromPlateauDtoMap.setProvider(plateauFromPlateauDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un plateau à partir de son id")
	PlateauDto get(@PathVariable("id") Integer id) throws Exception {
		PlateauDto plateauDto = modelMapper.map(
			plateauService.get(id),
			PlateauDto.class);
		return plateauDto;
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un plateau (créer en l'absence d'id ou modifier)")
	public PlateauDto save(@RequestBody PlateauDto plateauDto) throws Exception {
		return modelMapper.map(
			plateauService.save(modelMapper.map(plateauDto, Plateau.class)),
			PlateauDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un plateau à partir de son id")
	public void delete(@PathVariable("id") Integer id) throws Exception {
		plateauService.delete(id);
	}

	@GetMapping("/{id}/joueurs/{idJoueur}/inscriptions")
	@Operation(summary = "Obtenir l'inscription d'un joueur sur un plateau")
	InscriptionDto getInscription(@PathVariable("id") Integer id, @PathVariable("idJoueur") Integer idJoueur) throws Exception {
		PlateauJoueur plateauJoueur = plateauJoueurService.get(id, idJoueur);
		InscriptionDto inscriptionDto = new InscriptionDto();
		inscriptionDto.setIdPlateau(id);
		inscriptionDto.setIdJoueur(idJoueur);
		inscriptionDto.setInscrit(plateauJoueur != null);
		return inscriptionDto;
	}

	@PostMapping("/inscriptions")
	@Operation(summary = "Inscrire ou désinscrire un joueur sur un plateau")
	public InscriptionDto saveInscription(@RequestBody InscriptionDto inscriptionDto) throws Exception {
		inscriptionDto.setInscrit( 
			plateauJoueurService.saveInscription(
				inscriptionDto.getIdPlateau(), 
				inscriptionDto.getIdJoueur(),
				inscriptionDto.isInscrit()));
		return inscriptionDto;
	}

	@GetMapping("/{id}/joueurs/{idJoueur}/presences")
	@Operation(summary = "Obtenir la présence d'un joueur sur un plateau")
	PresenceDto getPresence(@PathVariable("id") Integer id, @PathVariable("idJoueur") Integer idJoueur) throws Exception {
		PlateauJoueur plateauJoueur = plateauJoueurService.get(id, idJoueur);
		if (plateauJoueur == null) {
			throw new BadRequestException("Aucune inscription enregistrée pour le joueur " + idJoueur + " sur le plateau " + id + ".");
		}
		PresenceDto presenceDto = new PresenceDto();
		presenceDto.setIdPlateau(id);
		presenceDto.setIdJoueur(idJoueur);
		presenceDto.setPresence(plateauJoueur.getPresence());
		return presenceDto;
	}

	@PostMapping("/presences")
	@Operation(summary = "Enregistrer la présence d'un joueur sur un plateau")
	public PresenceDto savePresence(@RequestBody PresenceDto presenceDto) throws Exception {
		presenceDto.setPresence(
			plateauJoueurService.savePresence(
				presenceDto.getIdPlateau(), 
				presenceDto.getIdJoueur(),
				presenceDto.getPresence()));
		return presenceDto;
	}
}