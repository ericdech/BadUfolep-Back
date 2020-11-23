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
import org.ufolep.bad.dto.PouleDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class PouleControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// Poule à tester
	private static PouleDto poule = new PouleDto();

	@Test
	@Order(1)
	public void createPoule() throws Exception {

		// Given
		PouleDto expected = poule;
		expected.setIdPlateau(1);
		expected.setIdCategorie(1);
		expected.setNumero(3);
		expected.setFeuilleMatch(new byte[10]);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/poules/", expected, String.class);
		final PouleDto response = 
			objectMapper.readValue(responseEntity.getBody(), PouleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idPoule");
		assertThat(response.getIdPoule()).isNotNull();
		expected.setIdPoule(response.getIdPoule());
	}

	@Test
	@Order(2)
	public void updatePoule() throws Exception {

		// Given
		PouleDto expected = poule;
		expected.setFeuilleMatch(new byte[20]);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/poules/", expected, String.class);
		final PouleDto response = 
			objectMapper.readValue(responseEntity.getBody(), PouleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readPoule() throws Exception {

		// Given
		PouleDto expected = poule;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/poules/" + expected.getIdPoule(), String.class);
		final PouleDto response = 
			objectMapper.readValue(responseEntity.getBody(), PouleDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deletePoule() throws Exception {

		// Given
		PouleDto expected = poule;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/poules/" + expected.getIdPoule(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}