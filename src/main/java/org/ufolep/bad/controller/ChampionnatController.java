package  org.ufolep.bad.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RestController;
import org.ufolep.bad.domain.Championnat;
import org.ufolep.bad.domain.Plateau;
import org.ufolep.bad.dto.CalendrierDto;
import org.ufolep.bad.dto.CategorieDto;
import org.ufolep.bad.dto.ChampionnatDto;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.dto.JourneeDto;
import org.ufolep.bad.dto.PlateauDto;
import org.ufolep.bad.service.CategorieService;
import org.ufolep.bad.service.ChampionnatService;
import org.ufolep.bad.service.PlateauService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Championnats", description="Championnats départementaux jeunes de badminton organisés par l'UFOLEP")
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
@RequestMapping("/championnats")
public class ChampionnatController {

	// Services
	private final ChampionnatService championnatService;
	private final PlateauService plateauService;
	private final CategorieService categorieService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public ChampionnatController(
		final ChampionnatService championnatService,
		final PlateauService plateauService,
		final CategorieService categorieService,
		final ModelMapper modelMapper) {
		this.championnatService = championnatService;
		this.plateauService = plateauService;
		this.categorieService = categorieService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<Championnat> championnatFromChampionnatDtoProvider = new Provider<Championnat>() {
			public Championnat get(ProvisionRequest<Championnat> request) {
				return new Championnat(((ChampionnatDto) request.getSource()).getIdChampionnat());
			}
		};
		TypeMap<ChampionnatDto, Championnat> championnatFromChampionnatDtoMap = this.modelMapper.createTypeMap(ChampionnatDto.class, Championnat.class);
		championnatFromChampionnatDtoMap.setProvider(championnatFromChampionnatDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un championnat à partir de son id")
	ChampionnatDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			championnatService.get(id),
			ChampionnatDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer un championnat (créer en l'absence d'id ou modifier)")
	public ChampionnatDto save(@RequestBody ChampionnatDto championnatDto) throws Exception {
		return modelMapper.map(
			championnatService.save(modelMapper.map(championnatDto, Championnat.class)),
			ChampionnatDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un championnat à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		championnatService.delete(id);
	}

	@GetMapping("/{id}/categories")
	@Operation(summary = "Obtenir les catégories d'un championnat")
	List<CategorieDto> getCategories(@PathVariable("id") Integer id) throws Exception {
		return Arrays.asList(
			modelMapper.map(
				categorieService.getByChampionnat(id),
				CategorieDto[].class));
	}

	@GetMapping("/{id}/calendrier")
	@Operation(summary = "Obtenir le calendrier d'un championnat")
	CalendrierDto getCalendrier(@PathVariable("id") Integer id) throws Exception {

		// Championnat
		Championnat championnat = championnatService.get(id);

		// Journee
		List<JourneeDto> journeeDtos = new ArrayList<>();
		if (championnat.getNbJournee() != null
			&& championnat.getNbJournee() > 0) {

			// Recherche des plateaux
			List<Plateau> plateaux = plateauService.getByChampionnat(championnat.getIdChampionnat());

			// Construction des journées
			for (int numeroJournee = 1 ; numeroJournee <= championnat.getNbJournee() ; numeroJournee++) {
				JourneeDto journeeDto = new JourneeDto();
				journeeDto.setNumeroJournee(numeroJournee);
				journeeDtos.add(journeeDto);

				// Plateaux de la journée
				journeeDto.setPlateaux(new ArrayList<PlateauDto>());
				for (Plateau plateau : plateaux) {
					if (plateau.getNumeroJournee() == numeroJournee) {
						journeeDto.getPlateaux().add(
							modelMapper.map(plateau, PlateauDto.class));		
					}
				}
			}
		}

		// Calendrier à retourner
		CalendrierDto calendrierDto = new CalendrierDto();
		calendrierDto.setIdChampionnat(championnat.getIdChampionnat());
		calendrierDto.setNbJournee(championnat.getNbJournee());
		calendrierDto.setJournees(journeeDtos);
		return calendrierDto;
	}
}