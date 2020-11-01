package  org.ufolep.bad.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ufolep.bad.domain.AdherentCst;
import org.ufolep.bad.domain.ChampionnatCst;
import org.ufolep.bad.dto.AdherentDto;
import org.ufolep.bad.dto.ChampionnatDto;
import org.ufolep.bad.dto.ClubDto;
import org.ufolep.bad.dto.SessionDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class AdherentControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// Adherent à tester
	private static AdherentDto adherent = new AdherentDto();

	@Test
	@Order(1)
	public void createAdherent() throws Exception {

		// Given
		AdherentDto expected = adherent;
		expected.setPseudo("Pseudo");
		expected.setPassword("Password");
		expected.setNom("Nom");
		expected.setPrenom("Prénom");
		expected.setDateNaissance(LocalDate.of(2000, 1, 1));
		expected.setSexe(AdherentCst.SEXE_M);
		expected.setLicence("Licence");
		expected.setTel("0202020202");
		expected.setEmail("email@email");
		expected.setAdministrateur(true);
		expected.setResponsableChampionnat(true);
		expected.setResponsableClub(true);
		expected.setIdClub(1);

		//  When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/adherents/", expected, String.class);
		final AdherentDto response = 
			objectMapper.readValue(responseEntity.getBody(), AdherentDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToIgnoringGivenFields(expected, "idAdherent");
		assertThat(response.getIdAdherent()).isNotNull();
		expected.setIdAdherent(response.getIdAdherent());
	}

	@Test
	@Order(2)
	public void updateAdherent() throws Exception {

		// Given
		AdherentDto expected = adherent;
		expected.setPseudo(expected.getPseudo() + "2");
		expected.setPseudo(expected.getPassword() + "2");
		expected.setPseudo(expected.getNom() + "2");
		expected.setPseudo(expected.getPrenom() + "2");
		expected.setDateNaissance(LocalDate.of(2000, 1, 2));
		expected.setSexe(AdherentCst.SEXE_F);
		expected.setLicence(expected.getLicence() + "2");
		expected.setTel(expected.getTel() + "2");
		expected.setEmail(expected.getEmail() + "2");
		expected.setAdministrateur(false);
		expected.setResponsableChampionnat(false);
		expected.setResponsableChampionnat(false);

		//  When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.postForEntity("http://localhost:" + port + "/adherents/", expected, String.class);
		final AdherentDto response = 
			objectMapper.readValue(responseEntity.getBody(), AdherentDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(3)
	public void readAdherent() throws Exception {

		// Given
		AdherentDto expected = adherent;

		//  When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity("http://localhost:" + port + "/adherents/" + expected.getIdAdherent(), String.class);
		final AdherentDto response = 
			objectMapper.readValue(responseEntity.getBody(), AdherentDto.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualToComparingFieldByField(expected);
	}

	@Test
	@Order(4)
	public void deleteAdherent() throws Exception {

		// Given
		AdherentDto expected = adherent;

		//  When
		final ResponseEntity<Void> responseEntity = 
			this.restTemplate
				.exchange(
					"http://localhost:" + port + "/adherents/" + expected.getIdAdherent(),
					HttpMethod.DELETE,
					HttpEntity.EMPTY,
				Void.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@Order(5)
	public void connect() throws Exception {

		// Given
		AdherentDto adherent = new AdherentDto();
		adherent.setIdAdherent(1);
		adherent.setPseudo("COCO");
		adherent.setPassword("COCO1");
		adherent.setNom("DECHARTRE");
		adherent.setPrenom("Colin");
		adherent.setDateNaissance(LocalDate.of(2003, 6, 16));
		adherent.setSexe(AdherentCst.SEXE_M);
		adherent.setLicence("001");
		adherent.setTel("0606060606");
		adherent.setEmail("email@email");
		adherent.setAdministrateur(false);
		adherent.setResponsableChampionnat(false);
		adherent.setResponsableClub(false);
		adherent.setIdClub(1);
		ClubDto club = new ClubDto();
		club.setIdClub(1);
		club.setUcClub("STCO");
		club.setLlClub("Saint Colomban");
		club.setRefFederation("001");
		club.setNomSalle("Espace Yannick Noah");
		club.setAdresse1Salle("");
		club.setAdresse2Salle("");
		club.setVilleSalle("");
		club.setEmail("email@email");
		club.setInvitationJoueurAutomatique(false);
		SessionDto expected = new SessionDto();
		expected.setAdherent(adherent);
		expected.setClub(club);

		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/adherents/connect?pseudo=" + adherent.getPseudo() 
					+ "&password=" + adherent.getPassword(),
					String.class);
		final SessionDto response = 
			objectMapper.readValue(responseEntity.getBody(), SessionDto.class);

		// Then
		assertThat(response).isEqualTo(expected);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}


	@Test
	@Order(6)
	public void getChampionnats() throws Exception {

		// Given
		Integer idAdherent = 1;
		ChampionnatDto championnat = new ChampionnatDto();
		championnat.setIdChampionnat(1);
		championnat.setUcChampionnat("UC1");
		championnat.setLlChampionnat("LL1");
		championnat.setLogoChampionnat(null);
		championnat.setNbJournee(6);
		championnat.setStatut(ChampionnatCst.STATUT_INITIAL);
		List<ChampionnatDto> expected = new ArrayList<ChampionnatDto>();
		expected.add(championnat);
		
		// When
		final ResponseEntity<String> responseEntity = 
			this.restTemplate
				.getForEntity(
					"http://localhost:" + port 
					+ "/adherents/" + idAdherent 
					+ "/championnats",
					String.class);
		final List<ChampionnatDto> response = 
			Arrays.asList(
				objectMapper.readValue(
					responseEntity.getBody(), 
					ChampionnatDto[].class));

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertThat(response).isEqualTo(expected);
	}
}