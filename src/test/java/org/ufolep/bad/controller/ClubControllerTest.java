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
import org.ufolep.bad.dto.ClubDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class ClubControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// expected à tester
	private static ClubDto club = new ClubDto();

	@Test
	@Order(1)
	public void createClub() throws Exception {

		// Given
		ClubDto expected = club;
		expected.setUcClub("ucexpected");
		expected.setLlClub("llexpected");
		expected.setRefFederation("refFederation");
		expected.setNomSalle("NomSalle");
		expected.setAdresse1Salle("adresse1Salle");
		expected.setAdresse2Salle("adresse2Salle");
		expected.setVilleSalle("villeSalle");
		expected.setEmail("eMail");
		expected.setInvitationJoueurAutomatique(true);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/clubs/", expected, String.class);
		final ClubDto response = 
			objectMapper.readValue(responseEntity.getBody(), ClubDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idClub");
		assertThat(response.getIdClub()).isNotNull();
		expected.setIdClub(response.getIdClub());
	}

	@Test
	@Order(2)
	public void updateClub() throws Exception {

		// Given
		ClubDto expected = club;
		expected.setUcClub(expected.getUcClub() + "2");
		expected.setLlClub(expected.getLlClub() + "2");
		expected.setRefFederation(expected.getRefFederation() + "2");
		expected.setNomSalle(expected.getNomSalle() + "2");
		expected.setAdresse1Salle(expected.getAdresse1Salle() + "2");
		expected.setAdresse2Salle(expected.getAdresse2Salle() + "2");
		expected.setVilleSalle(expected.getVilleSalle() + "2");
		expected.setEmail(expected.getEmail() + "2");
		expected.setInvitationJoueurAutomatique(false);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/clubs/", expected, String.class);
		final ClubDto reponse = 
			objectMapper.readValue(responseEntity.getBody(), ClubDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(reponse).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readClub() throws Exception {

		// Given
		ClubDto expected = club;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/clubs/" + expected.getIdClub(), String.class);
		final ClubDto response = 
			objectMapper.readValue(responseEntity.getBody(), ClubDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deleteClub() throws Exception {

		// Given
		ClubDto expected = club;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/clubs/" + expected.getIdClub(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}