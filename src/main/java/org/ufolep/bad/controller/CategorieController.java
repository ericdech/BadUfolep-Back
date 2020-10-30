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
import org.ufolep.bad.domain.Categorie;
import org.ufolep.bad.dto.CategorieDto;
import org.ufolep.bad.dto.ErrorDto;
import org.ufolep.bad.service.CategorieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name="Categories", description="Catégories des joueurs de badminton dans les championnats UFOLEP")
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
@RequestMapping("/categories")
public class CategorieController {

	// Services
	private final CategorieService categorieService;

	// Model mapper
	private ModelMapper modelMapper;

	@Autowired
	public CategorieController(
		final CategorieService categorieService,
		final ModelMapper modelMapper) {
		this.categorieService = categorieService;
		this.modelMapper = modelMapper;

		// Configuration du mapper
		Provider<Categorie> categorieFromCategorieDtoProvider = new Provider<Categorie>() {
			public Categorie get(ProvisionRequest<Categorie> request) {
				return new Categorie(((CategorieDto) request.getSource()).getIdCategorie());
			}
		};
		TypeMap<CategorieDto, Categorie> categorieFromCategorieDtoMap = this.modelMapper.createTypeMap(CategorieDto.class, Categorie.class);
		categorieFromCategorieDtoMap.setProvider(categorieFromCategorieDtoProvider);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtenir une catégorie à partir de son id")
	CategorieDto get(@PathVariable("id") Integer id) throws Exception {
		return modelMapper.map(
			categorieService.get(id),
			CategorieDto.class);
	}

	@PostMapping("")
	@Operation(summary = "Enregistrer une catégorie (créer en l'absence d'id ou modifier)")
	public CategorieDto save(@RequestBody CategorieDto categorieDto) throws Exception {
		return modelMapper.map(
			categorieService.save(modelMapper.map(categorieDto, Categorie.class)),
			CategorieDto.class);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer une catégorie à partir de son id")
	public void delete(@PathVariable("id") Integer id)throws Exception {
		categorieService.delete(id);
	}
}