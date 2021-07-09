package com.jokenpo.demo.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.service.PlayerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PlayerControllerTest {

	private static String HOST = "http://localhost";
	private static String ENDPOINT = "/v1/player";

	@LocalServerPort
	private int randomServerPort;

	private TestRestTemplate testRestTemplate;
	private RestTemplate restTemplate;
	private PlayerService playerService;

	@Before
	public void start() {
		testRestTemplate = new TestRestTemplate();
		restTemplate = new RestTemplate();
		playerService = new PlayerService();
	}

	@Test
	public void getPlayersAPI() throws URISyntaxException {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.getForEntity(getPlayerUrl(), String.class);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void addPlayerAPI() throws URISyntaxException {
		Player playerPost = new Player();
		playerPost.setName("Test");
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> result = testRestTemplate.postForEntity(getPlayerUrl(), playerPost, String.class);
		assertEquals(201, result.getStatusCodeValue());

	}

	@Test
	private void deletePlayerAPI() throws URISyntaxException {
		this.playerService.register(new Player("P1"));
		TestRestTemplate testRestTemplate = new TestRestTemplate();
//		ResponseEntity<String> result = testRestTemplate.delete(getPlayerUrl() + "/P1");
//		assertEquals(200, result.getStatusCodeValue());
		assertEquals(0, this.getAllPlayers().size());
	}

	private List<Player> getAllPlayers() throws URISyntaxException {
		return playerService.listAll();
	}

	private URI getPlayerUrl() throws URISyntaxException {
		final String baseUrl = HOST + ":" + randomServerPort + ENDPOINT;
		return new URI(baseUrl);
	}

}
