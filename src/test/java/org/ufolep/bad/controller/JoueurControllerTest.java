package  org.ufolep.bad.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

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
import org.ufolep.bad.dto.JoueurDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class JoueurControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// Joueur à tester
	private static JoueurDto joueur = new JoueurDto();

	@Test
	@Order(1)
	public void createJoueur() throws Exception {

		// Given
		JoueurDto expected = joueur;
		expected.setIdChampionnat(1);
		expected.setIdAdherent(4);
		expected.setIdCategorie(1);
		expected.setIdClub(1);
		expected.setIdJoueurPaire(1);
		expected.setPointMeilleursPlateaux(400);
		expected.setPointTousPlateaux(500);
		expected.setNiveau(new BigDecimal(120));
		expected.setClassement(1);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/joueurs/", expected, String.class);
		final JoueurDto response = 
			objectMapper.readValue(responseEntity.getBody(), JoueurDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idJoueur");
		assertThat(response.getIdJoueur()).isNotNull();
		expected.setIdJoueur(response.getIdJoueur());
	}

	@Test
	@Order(2)
	public void updateJoueur() throws Exception {

		// Given
		JoueurDto expected = joueur;
		expected.setIdClub(2);
		expected.setIdJoueurPaire(2);
		expected.setPointMeilleursPlateaux(expected.getPointMeilleursPlateaux() + 10);
		expected.setPointTousPlateaux(expected.getPointTousPlateaux() + 10);
		expected.setNiveau(expected.getNiveau().add(new BigDecimal(10)));
		expected.setClassement(1);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/joueurs/", expected, String.class);
		final JoueurDto response = 
			objectMapper.readValue(responseEntity.getBody(), JoueurDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readJoueur() throws Exception {

		// Given
		JoueurDto expected = joueur;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/joueurs/" + expected.getIdJoueur(), String.class);
		final JoueurDto response = 
			objectMapper.readValue(responseEntity.getBody(), JoueurDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deleteJoueur() throws Exception {

		// Given
		JoueurDto expected = joueur;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/joueurs/" + expected.getIdJoueur(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}