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
import org.ufolep.bad.domain.CategorieCst;
import org.ufolep.bad.dto.CategorieDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class CategorieControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// Categorie à tester
	private static CategorieDto categorie = new CategorieDto();

	@Test
	@Order(1)
	public void createCategorie() throws Exception {

		// Given
		CategorieDto expected = categorie;
		expected.setIdChampionnat(1);
		expected.setCategorieAge(CategorieCst.CATEGORIEAGE_PLUME1);
		expected.setSexe(CategorieCst.SEXE_M);
		expected.setAnneeNaissanceMin(2000);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/categories/", expected, String.class);
		final CategorieDto response = 
			objectMapper.readValue(responseEntity.getBody(), CategorieDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idCategorie");
		assertThat(response.getIdCategorie()).isNotNull();
		expected.setIdCategorie(response.getIdCategorie());
	}

	@Test
	@Order(2)
	public void updateCategorie() throws Exception {

		// Given
		CategorieDto expected = categorie;
		expected.setCategorieAge(CategorieCst.CATEGORIEAGE_PLUME2);
		expected.setSexe(CategorieCst.SEXE_F);
		expected.setAnneeNaissanceMin(2002);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/categories/", expected, String.class);
		final CategorieDto response = 
			objectMapper.readValue(responseEntity.getBody(), CategorieDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readCategorie() throws Exception {

		// Given
		CategorieDto expected = categorie;

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/categories/" + expected.getIdCategorie(), String.class);
		final CategorieDto response = 
			objectMapper.readValue(responseEntity.getBody(), CategorieDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deleteCategorie() throws Exception {

		// Given
		CategorieDto expected = categorie;

		// When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/categories/" + expected.getIdCategorie(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}