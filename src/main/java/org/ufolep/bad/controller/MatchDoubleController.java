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
import org.ufolep.bad.domain.MatchDouble;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.MatchDoubleDto;
import org.ufolep.bad.service.MatchDoubleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Matchs doubles", description="Matchs doubles joués dans les poules")
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
@RequestMapping("/match_doubles")
public class MatchDoubleController {

	// Services
	private final MatchDoubleService matchDoubleService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public MatchDoubleController(
		final MatchDoubleService matchDoubleService,
		final ModelMapper modelMapper) {
		this.matchDoubleService = matchDoubleService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<MatchDouble> matchDoubleFromMatchDoubleDtoProvider = new Provider<MatchDouble>() {
			public MatchDouble get(ProvisionRequest<MatchDouble> request) {
				return new MatchDouble(((MatchDoubleDto) request.getSource()).getIdMatchDouble());
			}
		};
		TypeMap<MatchDoubleDto, MatchDouble> matchDoubleFromMatchDoubleDtoMap = this.modelMapper.createTypeMap(MatchDoubleDto.class, MatchDouble.class);
		matchDoubleFromMatchDoubleDtoMap.setProvider(matchDoubleFromMatchDoubleDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un match double à partir de son id")
	MatchDoubleDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			matchDoubleService.get(id),
			MatchDoubleDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un match double (créer en l'absence d'id ou modifier)")
	public MatchDoubleDto save(@RequestBody MatchDoubleDto matchDoubleDto) throws Exception {
		return modelMapper.map(
			matchDoubleService.save(modelMapper.map(matchDoubleDto, MatchDouble.class)),
			MatchDoubleDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un match double à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		matchDoubleService.delete(id);
	}
}