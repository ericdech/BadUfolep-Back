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
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ufolep.bad.domain.PlateauCst;
import org.ufolep.bad.domain.PlateauJoueurCst;
import org.ufolep.bad.dto.CategorieDto;
import org.ufolep.bad.dto.InscriptionDto;
import org.ufolep.bad.dto.PlateauDto;
import org.ufolep.bad.dto.PresenceDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class PlateauControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// Plateau à tester
	private static PlateauDto plateau = new PlateauDto();

	// Categories disponibles pour le championnat
	List<CategorieDto> categories;

	@BeforeAll
	private void init() throws Exception {

		// Liste des catégories
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/championnats/1/categories", String.class);
		categories = 
		 	Arrays.asList(
				objectMapper.readValue(responseEntity.getBody(), 
				CategorieDto[].class));
	}

	@Test
	@Order(1)
	public void createPlateau() throws Exception {

		// Given
		PlateauDto expected = plateau;
		expected.setIdChampionnat(1);
		expected.setIdClub(1);
		expected.setNumeroJournee(2);
		expected.setDatePlateau(LocalDate.of(2020, 1, 1));
		expected.setHeureDebut(LocalTime.of(8, 30));
		expected.setHeureFin(LocalTime.of(13, 0));
		expected.setNomSalle("nomSalle");
		expected.setAdresse1Salle("adresse1Salle");
		expected.setAdresse2Salle("adresse2Salle");
		expected.setVilleSalle("villeSalle");
		expected.setStatut(PlateauCst.STATUT_INITIAL);
		expected.setCategories(new ArrayList<CategorieDto>());
		expected.getCategories().add(categories.get(0));

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/plateaux/", expected, String.class);
		final PlateauDto response = 
			objectMapper.readValue(responseEntity.getBody(), PlateauDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idPlateau");
		assertThat(response.getIdPlateau()).isNotNull();
		expected.setIdPlateau(response.getIdPlateau());
	}

	@Test
	@Order(2)
	public void updatePlateau() throws Exception {

		// Given
		PlateauDto expected = plateau;
		expected.setIdClub(2);
		expected.setNumeroJournee(3);
		expected.setDatePlateau(LocalDate.of(2020, 1, 2));
		expected.setHeureDebut(LocalTime.of(9, 0));
		expected.setHeureFin(LocalTime.of(12, 30));
		expected.setNomSalle(expected.getNomSalle() + "2");
		expected.setAdresse1Salle(expected.getAdresse1Salle() + "2");
		expected.setAdresse2Salle(expected.getAdresse2Salle() + "2");
		expected.setVilleSalle(expected.getVilleSalle() + "2");
		expected.setStatut(PlateauCst.STATUT_INSCRIPTION);
		expected.getCategories().add(categories.get(1));

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/plateaux/", expected, String.class);
		final PlateauDto response = 
			objectMapper.readValue(responseEntity.getBody(), PlateauDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readPlateau() throws Exception {

		// Given
		PlateauDto expected = plateau;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/plateaux/" + expected.getIdPlateau(), String.class);
		final PlateauDto response = 
			objectMapper.readValue(responseEntity.getBody(), PlateauDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deletePlateau() throws Exception {

		// Given
		PlateauDto expected = plateau;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/plateaux/" + expected.getIdPlateau(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@Order(5)
	public void getInscriptionTrue() throws Exception {

		// Given
		InscriptionDto expected = new InscriptionDto();
		expected.setIdPlateau(1);
		expected.setIdJoueur(1);
		expected.setInscrit(Boolean.TRUE);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/plateaux/" + expected.getIdPlateau()
					+ "/joueurs/" + expected.getIdJoueur()
					+ "/inscriptions",
					String.class);
		final InscriptionDto response = 
			objectMapper.readValue(responseEntity.getBody(), InscriptionDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(6)
	public void getInscriptionFalse() throws Exception {
	
		// Given
		InscriptionDto expected = new InscriptionDto();
		expected.setIdPlateau(1);
		expected.setIdJoueur(3);
		expected.setInscrit(Boolean.FALSE);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/plateaux/" + expected.getIdPlateau()
					+ "/joueurs/" + expected.getIdJoueur()
					+ "/inscriptions",
					String.class);
		final InscriptionDto response = 
			objectMapper.readValue(responseEntity.getBody(), InscriptionDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}


	@Test
	@Order(7)
	public void setInscription() throws Exception {
	
		// Given
		InscriptionDto expected = new InscriptionDto();
		expected.setIdPlateau(1);
		expected.setIdJoueur(3);
		expected.setInscrit(Boolean.TRUE);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/plateaux/inscriptions", expected, String.class);
		final InscriptionDto response = 
			objectMapper.readValue(responseEntity.getBody(), InscriptionDto.class);
	
		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(8)
	public void resetInscription() throws Exception {
	
		// Given
		InscriptionDto expected = new InscriptionDto();
		expected.setIdPlateau(1);
		expected.setIdJoueur(3);
		expected.setInscrit(Boolean.FALSE);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/plateaux/inscriptions", expected, String.class);
		final InscriptionDto response = 
			objectMapper.readValue(responseEntity.getBody(), InscriptionDto.class);
	
		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(9)
	public void getPresenceOK() throws Exception {
	
		// Given
		PresenceDto expected = new PresenceDto();
		expected.setIdPlateau(1);
		expected.setIdJoueur(1);
		expected.setPresence(PlateauJoueurCst.PRESENCE_ACONFIRMER);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/plateaux/" + expected.getIdPlateau()
					+ "/joueurs/" + expected.getIdJoueur()
					+ "/presences",
					String.class);
		final PresenceDto response = 
			objectMapper.readValue(responseEntity.getBody(), PresenceDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(10)
	public void getPresenceKO() throws Exception {
	
		// Given
		PresenceDto expected = new PresenceDto();
		expected.setIdPlateau(1);
		expected.setIdJoueur(3);
		expected.setPresence(PlateauJoueurCst.PRESENCE_ACONFIRMER);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/plateaux/" + expected.getIdPlateau()
					+ "/joueurs/" + expected.getIdJoueur()
					+ "/presences",
					String.class);

		// Then
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}	

	/**
	 * Test générique de la présence
	 */
	private void setPresence (Character presence) throws Exception {
	
		// Given
		PresenceDto expected = new PresenceDto();
		expected.setIdPlateau(1);
		expected.setIdJoueur(1);
		expected.setPresence(presence);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/plateaux/presences", expected, String.class);
		final PresenceDto response = 
			objectMapper.readValue(responseEntity.getBody(), PresenceDto.class);
	
		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(11)
	public void setAbsence() throws Exception {
		setPresence(PlateauJoueurCst.PRESENCE_ABSENT);
	}

	@Test
	@Order(12)
	public void setPresence() throws Exception {
		setPresence(PlateauJoueurCst.PRESENCE_PRESENT);
	}

	@Test
	@Order(13)
	public void setProvisoire() throws Exception {
		setPresence(PlateauJoueurCst.PRESENCE_ACONFIRMER);
	}	
}