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
import org.ufolep.bad.domain.Poule;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.PouleDto;
import org.ufolep.bad.service.PouleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Poules", description = "Poules des plateaux")
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
@RequestMapping("/poules")
public class PouleController {

	// Services
	private final PouleService pouleService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public PouleController(
		final PouleService pouleService,
		final ModelMapper modelMapper) {
		this.pouleService = pouleService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<Poule> pouleFromPouleDtoProvider = new Provider<Poule>() {
			public Poule get(ProvisionRequest<Poule> request) {
				return new Poule(((PouleDto) request.getSource()).getIdPoule());
			}
		};
		TypeMap<PouleDto, Poule> pouleFromPouleDtoMap = this.modelMapper.createTypeMap(PouleDto.class, Poule.class);
		pouleFromPouleDtoMap.setProvider(pouleFromPouleDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir une poule à partir de son id")
	PouleDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			pouleService.get(id),
			PouleDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer une poule (créer en l'absence d'id ou modifier)")
	public PouleDto save(@RequestBody PouleDto pouleDto) throws Exception {
		return modelMapper.map(
			pouleService.save(modelMapper.map(pouleDto, Poule.class)),
			PouleDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer une poule à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		pouleService.delete(id);
	}
}