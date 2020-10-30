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
import org.ufolep.bad.domain.Joueur;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.JoueurDto;
import org.ufolep.bad.service.JoueurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Joueurs", description="Joueurs participant aux championnats de badminton")
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
@RequestMapping("/joueurs")
public class JoueurController {

	// Services
	private final JoueurService joueurService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public JoueurController(
		final JoueurService joueurService,
		final ModelMapper modelMapper) {
		this.joueurService = joueurService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<Joueur> joueurFromJoueurDtoProvider = new Provider<Joueur>() {
			public Joueur get(ProvisionRequest<Joueur> request) {
				return new Joueur(((JoueurDto) request.getSource()).getIdJoueur());
			}
		};
		TypeMap<JoueurDto, Joueur> joueurFromJoueurDtoMap = this.modelMapper.createTypeMap(JoueurDto.class, Joueur.class);
		joueurFromJoueurDtoMap.setProvider(joueurFromJoueurDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un joueur à partir de son id")
	JoueurDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			joueurService.get(id),
			JoueurDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un joueur (créer en l'absence d'id ou modifier)")
	public JoueurDto save(@RequestBody JoueurDto joueurDto) throws Exception {
		return modelMapper.map(
			joueurService.save(modelMapper.map(joueurDto, Joueur.class)),
			JoueurDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un joueur à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		joueurService.delete(id);
	}
}