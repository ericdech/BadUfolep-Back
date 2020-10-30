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
import org.ufolep.bad.domain.PlateauJoueur;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.PlateauJoueurDto;
import org.ufolep.bad.service.PlateauJoueurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Plateaux Joueurs", description="Joueurs inscrits sur les plateaux")
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
@RequestMapping("/plateau_joueurs")
public class PlateauJoueurController {

	// Services
	private final PlateauJoueurService plateauJoueurService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public PlateauJoueurController(
	final PlateauJoueurService plateauJoueurService,
	final ModelMapper modelMapper) {
		this.plateauJoueurService = plateauJoueurService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<PlateauJoueur> plateauJoueurFromPlateauJoueurDtoProvider = new Provider<PlateauJoueur>() {
			public PlateauJoueur get(ProvisionRequest<PlateauJoueur> request) {
				return new PlateauJoueur(((PlateauJoueurDto) request.getSource()).getIdPlateauJoueur());
			}
		};
		TypeMap<PlateauJoueurDto, PlateauJoueur> plateauJoueurFromPlateauJoueurDtoMap = this.modelMapper.createTypeMap(PlateauJoueurDto.class, PlateauJoueur.class);
		plateauJoueurFromPlateauJoueurDtoMap.setProvider(plateauJoueurFromPlateauJoueurDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un joueur à partir de son id d'inscription sur un plateau")
	PlateauJoueurDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			plateauJoueurService.get(id),
			PlateauJoueurDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un joueur sur un plateau (créer en l'absence d'id ou modifier)")
	public PlateauJoueurDto save(@RequestBody PlateauJoueurDto plateauJoueurDto) throws Exception {
		return modelMapper.map(
			plateauJoueurService.save(modelMapper.map(plateauJoueurDto, PlateauJoueur.class)),
			PlateauJoueurDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un joueur sur un plateau à partir de son id d'inscription")
	public void delete(@PathVariable("id") Integer id) throws Exception {
		plateauJoueurService.delete(id);
	}
}