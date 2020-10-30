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
import org.ufolep.bad.domain.Club;
import org.ufolep.bad.dto.ClubDto;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.service.ClubService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Clubs", description="Clubs de badminton affiliés à l'UFOLEP")
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
@RequestMapping("/clubs")
public class ClubController {

	// Services
	private final ClubService clubService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public ClubController(
		final ClubService clubService,
		final ModelMapper modelMapper) {
		this.clubService = clubService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<Club> clubFromClubDtoProvider = new Provider<Club>() {
			public Club get(ProvisionRequest<Club> request) {
				return new Club(((ClubDto) request.getSource()).getIdClub());
			}
		};
		TypeMap<ClubDto, Club> clubFromClubDtoMap = this.modelMapper.createTypeMap(ClubDto.class, Club.class);
		clubFromClubDtoMap.setProvider(clubFromClubDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un club à partir de son id")
	ClubDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			clubService.get(id),
			ClubDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un club (créer en l'absence d'id ou modifier)")
	public ClubDto save(@RequestBody ClubDto clubDto) throws Exception {
		return modelMapper.map(
			clubService.save(modelMapper.map(clubDto, Club.class)),
			ClubDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un ckub à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		clubService.delete(id);
	}
}