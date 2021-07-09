package com.jokenpo.demo.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.jokenpo.demo.enums.JokenpoEnum;
import com.jokenpo.demo.model.Move;
import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.service.MoveService;
import com.jokenpo.demo.service.PlayerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class MoveControllerTest {

	@LocalServerPort
	private int randomServerPort;

	private static String HOST = "http://localhost";
	private static String ENDPOINT = "/v1/move";

	private TestRestTemplate testRestTemplate;
	private MoveService moveService;
	private PlayerService playerService;

	@Before
	public void start() {
		testRestTemplate = new TestRestTemplate();
	}

	@Test
	public void getListMovesAPI() throws URISyntaxException {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.getForEntity(getMoveUri(), String.class);

		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void addMoveAPI() throws URISyntaxException {
		createPlayerForTest();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Integer> map = new LinkedMultiValueMap<String, Integer>();
		map.add("P1", JokenpoEnum.PAPEL.getJokenpoId());
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map,
				headers);
		ResponseEntity<String> result = testRestTemplate.postForEntity(getMoveUri(), request, String.class);
		assertEquals(201, result.getStatusCodeValue());
	}

	@Test
	public void deleteMoveByNameAPI() throws URISyntaxException {
		createPlayerForTest();
		this.moveService.createMove("MH", JokenpoEnum.PAPEL.getJokenpoId());
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		testRestTemplate.delete(getMoveUri() + "/?namePlayer=P1");
		assertEquals(0, this.getAllMoves().size());
	}

	private List<Move> getAllMoves() throws URISyntaxException {
		List<Move> list = this.moveService.listAllMove();
		return list;
	}

	private URI getMoveUri() throws URISyntaxException {
		final String baseUrl = HOST + ":" + randomServerPort + ENDPOINT;
		return new URI(baseUrl);
	}

	private void createPlayerForTest() {
		this.playerService.clearAll();
		Player playerTest = new Player();
		playerTest.setName("P1");
		this.playerService.register(playerTest);
	}
}
