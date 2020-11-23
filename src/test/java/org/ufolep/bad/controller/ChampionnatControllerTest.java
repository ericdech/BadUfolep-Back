package  org.ufolep.bad.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ufolep.bad.domain.CategorieCst;
import org.ufolep.bad.domain.ChampionnatCst;
import org.ufolep.bad.domain.PlateauCst;
import org.ufolep.bad.dto.CalendrierDto;
import org.ufolep.bad.dto.CategorieDto;
import org.ufolep.bad.dto.ChampionnatDto;
import org.ufolep.bad.dto.JourneeDto;
import org.ufolep.bad.dto.PlateauDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class ChampionnatControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// Championnat à tester
	private static ChampionnatDto championnat = new ChampionnatDto();

	@Test
	@Order(1)
	public void createChampionnat() throws Exception {

		// Given
		ChampionnatDto expected = championnat;
		expected.setUcChampionnat("ucChampionnat");
		expected.setLlChampionnat("llChampionnat");
		expected.setNbJournee(5);
		expected.setStatut(ChampionnatCst.STATUT_INITIAL);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/championnats/", expected, String.class);
		final ChampionnatDto response = 
			objectMapper.readValue(responseEntity.getBody(), ChampionnatDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idChampionnat");
		assertThat(response.getIdChampionnat()).isNotNull();
		expected.setIdChampionnat(response.getIdChampionnat());
	}

	@Test
	@Order(2)
	public void updateChampionnat() throws Exception {

		// Given
		ChampionnatDto expected = championnat;
		expected.setUcChampionnat(expected.getUcChampionnat() + "2");
		expected.setLlChampionnat(expected.getLlChampionnat() + "2");
		expected.setNbJournee(6);
		expected.setStatut(ChampionnatCst.STATUT_INITIAL);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/championnats/", expected, String.class);
		final ChampionnatDto response = 
			objectMapper.readValue(responseEntity.getBody(), ChampionnatDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readChampionnat() throws Exception {

		// Given
		ChampionnatDto expected = championnat;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/championnats/" + expected.getIdChampionnat(), String.class);
		final ChampionnatDto response = 
			objectMapper.readValue(responseEntity.getBody(), ChampionnatDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deleteChampionnat() throws Exception {

		// Given
		ChampionnatDto expected = championnat;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/championnats/" + expected.getIdChampionnat(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@Order(5)
	public void getCategories() throws Exception {

		// Given
		Integer idChampionnat = 1;
		CategorieDto categorie1 = new CategorieDto();
		categorie1.setIdCategorie(1);
		categorie1.setIdChampionnat(idChampionnat);
		categorie1.setCategorieAge(CategorieCst.CATEGORIEAGE_SMASH1);
		categorie1.setSexe(CategorieCst.SEXE_M);
		categorie1.setAnneeNaissanceMin(2005);
		CategorieDto categorie2 = new CategorieDto();
		categorie2.setIdCategorie(2);
		categorie2.setIdChampionnat(idChampionnat);
		categorie2.setCategorieAge(CategorieCst.CATEGORIEAGE_SMASH2);
		categorie2.setSexe(CategorieCst.SEXE_M);
		categorie2.setAnneeNaissanceMin(2003);
		List<CategorieDto> expected = new ArrayList<CategorieDto>();
		expected.add(categorie1);
		expected.add(categorie2);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/championnats/" + idChampionnat
					+ "/categories", 
					String.class);
		final List<CategorieDto> response = 
			Arrays.asList(
				objectMapper.readValue(
					responseEntity.getBody(), 
					CategorieDto[].class));

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualTo(expected);
	}

	@Test
	@Order(6)
	public void getCalendrier() throws Exception {

		// Given
		Integer idChampionnat = 1;

		// Journee 1
		JourneeDto journee1 = new JourneeDto();
		journee1.setNumeroJournee(1);
		PlateauDto plateau11 = new PlateauDto();
		plateau11.setIdPlateau(1);
		plateau11.setIdChampionnat(idChampionnat);
		plateau11.setIdClub(1);
		plateau11.setNumeroJournee(1);
		plateau11.setDatePlateau(LocalDate.of(2020, 2, 1));
		plateau11.setHeureDebut(LocalTime.of(8, 30));
		plateau11.setHeureFin(LocalTime.of(13, 30));
		plateau11.setNomSalle("Espace Yannick Noah");
		plateau11.setAdresse1Salle("");
		plateau11.setAdresse2Salle("");
		plateau11.setVilleSalle(null);
		plateau11.setStatut(PlateauCst.STATUT_INITIAL);
		CategorieDto categorie111 = new CategorieDto();
		categorie111.setIdCategorie(1);
		categorie111.setIdChampionnat(idChampionnat);
		categorie111.setCategorieAge(CategorieCst.CATEGORIEAGE_SMASH1);
		categorie111.setSexe(CategorieCst.SEXE_M);
		categorie111.setAnneeNaissanceMin(2005);
		CategorieDto categorie112 = new CategorieDto();
		categorie112.setIdCategorie(2);
		categorie112.setIdChampionnat(idChampionnat);
		categorie112.setCategorieAge(CategorieCst.CATEGORIEAGE_SMASH2);
		categorie112.setSexe(CategorieCst.SEXE_M);
		categorie112.setAnneeNaissanceMin(2003);
		List<CategorieDto> categories11 = new ArrayList<CategorieDto>();
		categories11.add(categorie111);
		categories11.add(categorie112);
		plateau11.setCategories(categories11);
		List<PlateauDto> plateaux1 = new ArrayList<PlateauDto>();
		plateaux1.add(plateau11);
		journee1.setPlateaux(plateaux1);

		// Journee 2
		JourneeDto journee2 = new JourneeDto();
		journee2.setNumeroJournee(2);
		journee2.setPlateaux(new ArrayList<PlateauDto>());

		// Journee 3
		JourneeDto journee3 = new JourneeDto();
		journee3.setNumeroJournee(3);
		journee3.setPlateaux(new ArrayList<PlateauDto>());

		// Journee 4
		JourneeDto journee4 = new JourneeDto();
		journee4.setNumeroJournee(4);
		journee4.setPlateaux(new ArrayList<PlateauDto>());

		// Journee 5
		JourneeDto journee5 = new JourneeDto();
		journee5.setNumeroJournee(5);
		journee5.setPlateaux(new ArrayList<PlateauDto>());

		// Journee 6
		JourneeDto journee6 = new JourneeDto();
		journee6.setNumeroJournee(6);
		journee6.setPlateaux(new ArrayList<PlateauDto>());

		// Calendrier
		CalendrierDto expected = new CalendrierDto();
		expected.setIdChampionnat(idChampionnat);
		expected.setNbJournee(6);
		List<JourneeDto> journees = new ArrayList<JourneeDto>();
		journees.add(journee1);
		journees.add(journee2);
		journees.add(journee3);
		journees.add(journee4);
		journees.add(journee5);
		journees.add(journee6);
		expected.setJournees(journees);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/championnats/" + idChampionnat
					+ "/calendrier", 
					String.class);
		final CalendrierDto response = 
			objectMapper.readValue(
				responseEntity.getBody(), 
				CalendrierDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualTo(expected);
	}
}