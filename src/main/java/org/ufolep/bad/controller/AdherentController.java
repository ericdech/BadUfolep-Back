package  org.ufolep.bad.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ufolep.bad.domain.Adherent;
import org.ufolep.bad.domain.Championnat;
import org.ufolep.bad.domain.Club;
import org.ufolep.bad.dto.AdherentDto;
import org.ufolep.bad.dto.ChampionnatDto;
import org.ufolep.bad.dto.ClubDto;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.SessionDto;
import org.ufolep.bad.service.AdherentService;
import org.ufolep.bad.service.ClubService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name="Adherents", description="Adhérents UFOLEP (responsable ou joueur de badminton)")
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
@RequestMapping("/adherents")
public class AdherentController {

	// Services
	private final AdherentService adherentService;
	private final ClubService clubService;

	// Model mapper
	private final ModelMapper modelMapper;

	@Autowired
	public AdherentController(
	final AdherentService adherentService, 
	final ClubService clubService, 
	final ModelMapper modelMapper) {
		this.adherentService = adherentService;
		this.clubService = clubService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<Adherent> adherentFromAdherentDtoProvider = new Provider<Adherent>() {
			public Adherent get(ProvisionRequest<Adherent> request) {
				return new Adherent(((AdherentDto) request.getSource()).getIdAdherent());
			}
		};
		TypeMap<AdherentDto, Adherent> adherentFromAdherentDtoMap = this.modelMapper.createTypeMap(AdherentDto.class, Adherent.class);
		adherentFromAdherentDtoMap.setProvider(adherentFromAdherentDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un adhérent à partir de son id")
	AdherentDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			adherentService.get(id),
			AdherentDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un adhérent (créer en l'absence d'id ou modifier)")
	public AdherentDto save(@RequestBody AdherentDto adherentDto) throws Exception {
		return modelMapper.map(
			adherentService.save(modelMapper.map(adherentDto, Adherent.class)),
			AdherentDto.class);

	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un adhérent à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		adherentService.delete(id);
	}

	@GetMapping("/connect")
	@Operation(summary = "Ouvrir une session à partir d'un pseudo et d'un mot de passe")
	SessionDto connect(@RequestParam("pseudo") String pseudo, @RequestParam("password") String password) throws Exception {

		// Recherche de l'adherent
		Adherent adherent = adherentService.connect(pseudo, password);

		// Recherche du club
		Club club = null;
		if (adherent.getIdClub() != null) {
			club = clubService.get(adherent.getIdClub());
		}

		// Transformation DTO
		SessionDto sessionDto = new SessionDto();
		sessionDto.setAdherent(modelMapper.map(adherent, AdherentDto.class));
		if (club != null) {
			sessionDto.setClub(modelMapper.map(club, ClubDto.class));
		}

		// Return
		return sessionDto;
	}

	@GetMapping("/{id}/championnats")
	@Operation(summary = "Obtenir les championnats auxquel un adhérent a participé")
	public List<ChampionnatDto> getChampionnats(@PathVariable("id") Integer idAdherent) throws Exception {

		// Recherche des championnats
		List<Championnat> championnats = adherentService.getChampionnats(idAdherent);

		// Transformation DTO
		List<ChampionnatDto> championnatDtos = new ArrayList<ChampionnatDto>();
		for (Championnat championnat:championnats) {
			ChampionnatDto championnatDto = modelMapper.map(championnat, ChampionnatDto.class);

			championnatDtos.add(championnatDto);
		}
		return championnatDtos;
	}
}