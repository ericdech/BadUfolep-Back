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
import org.ufolep.bad.domain.MatchSimple;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.MatchSimpleDto;
import org.ufolep.bad.service.MatchSimpleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Matchs simples", description="Matchs simples joués dans les poules")
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
@RequestMapping("/match_simples")
public class MatchSimpleController {

	// Services
	private final MatchSimpleService matchSimpleService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public MatchSimpleController(
		final MatchSimpleService matchSimpleService,
		final ModelMapper modelMapper) {
		this.matchSimpleService = matchSimpleService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<MatchSimple> matchSimpleFromMatchSimpleDtoProvider = new Provider<MatchSimple>() {
			public MatchSimple get(ProvisionRequest<MatchSimple> request) {
				return new MatchSimple(((MatchSimpleDto) request.getSource()).getIdMatchSimple());
			}
		};
		TypeMap<MatchSimpleDto, MatchSimple> matchSimpleFromMatchSimpleDtoMap = this.modelMapper.createTypeMap(MatchSimpleDto.class, MatchSimple.class);
		matchSimpleFromMatchSimpleDtoMap.setProvider(matchSimpleFromMatchSimpleDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un match simple à partir de son id")
	MatchSimpleDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			matchSimpleService.get(id),
			MatchSimpleDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un match simple (créer en l'absence d'id ou modifier)")
	public MatchSimpleDto save(@RequestBody MatchSimpleDto matchSimpleDto) throws Exception {
		return modelMapper.map(
			matchSimpleService.save(modelMapper.map(matchSimpleDto, MatchSimple.class)),
			MatchSimpleDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un match simple à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		matchSimpleService.delete(id);
	}
}