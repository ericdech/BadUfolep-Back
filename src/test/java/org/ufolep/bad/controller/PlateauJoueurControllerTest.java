package  org.ufolep.bad.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ufolep.bad.domain.PlateauJoueurCst;
import org.ufolep.bad.dto.PlateauJoueurDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class PlateauJoueurControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// PlateauJoueur à tester
	private static PlateauJoueurDto plateauJoueur = new PlateauJoueurDto();

	@Test
	@Order(1)
	public void createPlateauJoueur() throws Exception {

		// Given
		PlateauJoueurDto expected = plateauJoueur;
		expected.setIdPlateau(1);
		expected.setIdJoueur(3);
		expected.setIdPlateauJoueurPaire(1);
		expected.setIdPoule(1);
		expected.setPresence(PlateauJoueurCst.PRESENCE_ACONFIRMER);
		expected.setPoint(100);
		expected.setClassement(1);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/plateau_joueurs/", expected, String.class);
		final PlateauJoueurDto response = 
			objectMapper.readValue(responseEntity.getBody(), PlateauJoueurDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idPlateauJoueur");
		assertThat(response.getIdPlateauJoueur()).isNotNull();
		expected.setIdPlateauJoueur(response.getIdPlateauJoueur());
	}

	@Test
	@Order(2)
	public void updatePlateauJoueur() throws Exception {

		// Given
		PlateauJoueurDto expected = plateauJoueur;
		expected.setIdPlateauJoueurPaire(2);
		expected.setIdPoule(2);
		expected.setPresence(PlateauJoueurCst.PRESENCE_PRESENT);
		expected.setPoint(expected.getPoint() + 10);
		expected.setClassement(expected.getClassement() + 10);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/plateau_joueurs/", expected, String.class);
		final PlateauJoueurDto response = 
			objectMapper.readValue(responseEntity.getBody(), PlateauJoueurDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readPlateauJoueur() throws Exception {

		// Given
		PlateauJoueurDto expected = plateauJoueur;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/plateau_joueurs/" + expected.getIdPlateauJoueur(), String.class);
		final PlateauJoueurDto response = 
			objectMapper.readValue(responseEntity.getBody(), PlateauJoueurDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deletePlateauJoueur() throws Exception {

		// Given
		PlateauJoueurDto expected = plateauJoueur;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/plateau_joueurs/" + expected.getIdPlateauJoueur(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}