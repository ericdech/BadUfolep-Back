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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ufolep.bad.dto.MatchSimpleDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class MatchSimpleControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// MatchSimple à tester
	private static MatchSimpleDto matchSimple = new MatchSimpleDto();

	@Test
	@Order(1)
	public void createMatchSimple() throws Exception {

		// Given
		MatchSimpleDto expected = matchSimple;
		expected.setIdPlateauJoueur1(1);
		expected.setIdPlateauJoueur2(2);
		expected.setIdPoule(1);
		expected.setScoreJoueur1(10);
		expected.setScoreJoueur2(21);
		expected.setBonusJoueur1(1);
		expected.setBonusJoueur2(5);
		expected.setAnnotation("Contestation joueur 1");

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/match_simples/", expected, String.class);
		final MatchSimpleDto response = 
			objectMapper.readValue(responseEntity.getBody(), MatchSimpleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idMatchSimple");
		assertThat(response.getIdMatchSimple()).isNotNull();
		expected.setIdMatchSimple(response.getIdMatchSimple());
	}

	@Test
	@Order(2)
	public void updateMatchSimple() throws Exception {

		// Given
		MatchSimpleDto expected = matchSimple;
		expected.setScoreJoueur1(21);
		expected.setScoreJoueur2(10);
		expected.setBonusJoueur1(5);
		expected.setBonusJoueur2(1);
		expected.setAnnotation("Inversion score suite à contestation");

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/match_simples/", expected, String.class);
		final MatchSimpleDto response = 
			objectMapper.readValue(responseEntity.getBody(), MatchSimpleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readMatchSimple() throws Exception {

		// Given
		MatchSimpleDto expected = matchSimple;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/match_simples/" + expected.getIdMatchSimple(), String.class);
		final MatchSimpleDto response = 
			objectMapper.readValue(responseEntity.getBody(), MatchSimpleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deleteMatchSimple() throws Exception {

		// Given
		MatchSimpleDto expected = matchSimple;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/match_simples/" + expected.getIdMatchSimple(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}