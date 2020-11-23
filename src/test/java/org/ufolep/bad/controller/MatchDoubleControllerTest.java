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
import org.ufolep.bad.dto.MatchDoubleDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class MatchDoubleControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// MatchDouble à tester
	private static MatchDoubleDto matchDouble = new MatchDoubleDto();

	@Test
	@Order(1)
	public void createMatchDouble() throws Exception {

		// Given
		MatchDoubleDto expected = matchDouble;
		expected.setIdPlateauJoueur1Paire1(1);
		expected.setIdPlateauJoueur1Paire2(1);
		expected.setIdPlateauJoueur2Paire1(2);
		expected.setIdPlateauJoueur2Paire2(2);
		expected.setIdPoule(1);
		expected.setScorePaire1(10);
		expected.setScorePaire2(21);
		expected.setBonusPaire1(1);
		expected.setBonusPaire2(5);
		expected.setAnnotation("Contestation paire 1");

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/match_doubles/", expected, String.class);
		final MatchDoubleDto response = 
			objectMapper.readValue(responseEntity.getBody(), MatchDoubleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idMatchDouble");
		assertThat(response.getIdMatchDouble()).isNotNull();
		expected.setIdMatchDouble(response.getIdMatchDouble());
	}

	@Test
	@Order(2)
	public void updateMatchDouble() throws Exception {

		// Given
		MatchDoubleDto expected = matchDouble;
		expected.setScorePaire1(21);
		expected.setScorePaire2(10);
		expected.setBonusPaire1(5);
		expected.setBonusPaire2(1);
		expected.setAnnotation("Inversion score suite à contestation");

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/match_doubles/", expected, String.class);
		final MatchDoubleDto response = 
			objectMapper.readValue(responseEntity.getBody(), MatchDoubleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readMatchDouble() throws Exception {

		// Given
		MatchDoubleDto expected = matchDouble;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/match_doubles/" + expected.getIdMatchDouble(), String.class);
		final MatchDoubleDto response = 
			objectMapper.readValue(responseEntity.getBody(), MatchDoubleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deleteMatchDouble() throws Exception {

		// Given
		MatchDoubleDto expected = matchDouble;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/match_doubles/" + expected.getIdMatchDouble(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}