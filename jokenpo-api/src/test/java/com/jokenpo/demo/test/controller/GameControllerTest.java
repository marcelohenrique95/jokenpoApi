package com.jokenpo.demo.test.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.jokenpo.demo.enums.JokenpoEnum;
import com.jokenpo.demo.model.Move;
import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.service.MoveService;
import com.jokenpo.demo.service.PlayerService;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
public class GameControllerTest {

	private static String HOST = "http://localhost";
	private static String ENDPOINT = "/v1/game";

	@LocalServerPort
	private int randomServerPort;

	private RestTemplate restTemplate;
	private MoveService moveService;
	private PlayerService playerService;

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
		moveService = new MoveService();
		playerService = new PlayerService();
	}
	
	public void getListGames() {
		
	}
	
    @Test
    public void playWithNobodyWonAPI() throws URISyntaxException {
        this.playerService.clearAll();
        this.moveService.clearAll();
        this.playerService.register(new Player("P1"));
        this.playerService.register(new Player("P2"));
        this.playerService.register(new Player("P3"));
//        this.moveService.createMove(var,1);
//        this.moveService.createMove(new Move("P2", JokenpoEnum.SCISSORS.getName()));
//        this.moveService.createMove(new Move("P3", JokenpoEnum.PAPER.getName()));
        // Play
        ResponseEntity<String> result = restTemplate.getForEntity(getJGameUrl(), String.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().contains("data"));
        assertEquals(true, result.getBody().contains("history"));
        assertEquals(true, result.getBody().contains("result"));
        assertEquals(true, result.getBody().contains("NOBODY WON"));
    }
    
    private URI getJGameUrl() throws URISyntaxException {
        final String baseUrl = HOST + ":" + randomServerPort + ENDPOINT;
        return new URI(baseUrl);
    }


}
