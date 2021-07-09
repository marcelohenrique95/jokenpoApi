package com.jokenpo.demo.test.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jokenpo.demo.enums.JokenpoEnum;
import com.jokenpo.demo.exception.NegocioException;
import com.jokenpo.demo.model.GameResult;
import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.service.GameService;
import com.jokenpo.demo.service.MoveService;
import com.jokenpo.demo.service.PlayerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GameServiceTest {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MoveService moveService;

	@Autowired
	private GameService gameService;
	
	private GameResult gameResult;

	@Test
	public void clearAllDataWithSucess() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();
		createPlayer();	
		registerTwoMoves();
		assertNotEquals(0, this.playerService.listAll().size());
		assertNotEquals(0, this.moveService.listAllMove().size());
		this.playerService.clearAll();
		assertEquals(0, this.playerService.listAll().size());
		assertEquals(0, this.moveService.listAllMove().size());
	}

	@Test
	public void paperVsScissors() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();	
		createPlayer();	
		this.moveService.createMove("P1", JokenpoEnum.PAPEL.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.TESOURA.getJokenpoId());
		List<String> expected = verifyExpected();
		List<String> result = this.gameService.gaming();
		assertEquals(expected, result);
	}
	
	@Test
	public void paperVsStone() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();	
		createPlayer();	
		this.moveService.createMove("P1", JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.PAPEL.getJokenpoId());
		List<String> expected = verifyExpected();
		List<String> result = this.gameService.gaming();
		assertEquals(expected, result);
	}
	
	@Test
	public void scissorsVsStone() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();	
		createPlayer();	
		this.moveService.createMove("P1", JokenpoEnum.TESOURA.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.PEDRA.getJokenpoId());
		List<String> expected = verifyExpected();
		List<String> result = this.gameService.gaming();
		assertEquals(expected, result);
	}
	
	
	@Test
	public void PaperVsPaper() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();	
		createPlayer();	
		this.moveService.createMove("P1", JokenpoEnum.PAPEL.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.PAPEL.getJokenpoId());
		List<String> result = this.gameService.gaming();
		List<String> emptyList = new ArrayList<>();
		Optional<String> opt2 = emptyList.stream().findFirst();
		assertThrows(NegocioException.class, () -> opt2.get());
	}

	private List<Player> registerPlayers(List<String> namePlayers) throws Exception {
		List<Player> list = new ArrayList<>();
		for (String name : namePlayers) {
			Player player = this.playerService.register(new Player(name));
			list.add(player);
		}
		return list;
	}

	

	public List<Player> registerManyPlayers(List<String> namePlayers) throws Exception {
		List<Player> list = new ArrayList<>();
		for (String name : namePlayers) {
			Player player = this.playerService.register(new Player(name));
			list.add(player);
		}
		return list;
	}

	private List<Player> registerSamePlayers(List<String> playerNameList) {
		List<Player> list = new ArrayList<>();
		playerNameList.stream().forEach(playerName -> {
			try {
				list.add(this.playerService.register(new Player(playerName)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return list;
	}

	private void clearAll() {
		this.playerService.clearAll();
		this.moveService.clearAll();
	}
	
	private void createPlayer() throws Exception {
		this.playerService.clearAll();
		List<String> namePlayers = new ArrayList<String>(asList("P1", "P2", "P3","P4","P5"));
		this.registerManyPlayers(namePlayers);
	}
	
	public void registerTwoMoves() throws Exception {
		this.playerService.clearAll();
		this.createPlayer();
		this.moveService.createMove("P1", JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.PAPEL.getJokenpoId());
	}
	
	public List<String> verifyExpected() {
		List<String> expected = new ArrayList();
		expected.add("O Jogador [P1] perdeu e foi removido.");
		expected.add("O jogador [P2] venceu.");
		return expected;
	}

}
