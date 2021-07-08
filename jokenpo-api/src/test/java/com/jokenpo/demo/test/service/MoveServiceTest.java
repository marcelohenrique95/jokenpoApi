package com.jokenpo.demo.test.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jokenpo.demo.enums.JokenpoEnum;
import com.jokenpo.demo.exception.NegocioException;
import com.jokenpo.demo.model.Move;
import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.repository.PlayerRepository;
import com.jokenpo.demo.service.MoveService;
import com.jokenpo.demo.service.PlayerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MoveServiceTest {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MoveService moveService;
	
	@Autowired
	private PlayerRepository playerRepository;

	@Test
	public void registerManyPlayerSucesss() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();
		List<String> namePlayers = new ArrayList<>(asList("P1", "P2", "P3", "P4", "P5", "P6"));
		List<Player> player = this.insertDifferentPlayers(namePlayers);
		assertEquals(namePlayers.size(), player.size());
	}

	@Test
	public void playersWithoutMovements() throws Exception {
		this.registerManyPlayerSucesss();
		int playersCounter = this.playerService.listAll().size();
		int movementsCounter = this.moveService.listAllMove().size();
		assertEquals(0, movementsCounter);
		assertNotEquals(0, playersCounter);
	}

	@Test
	public void registerOneMove() throws Exception {
		this.registerManyPlayerSucesss();
		int expected = 1;
		Long playerId = (long) 5;
		Move move = this.moveService.createMove(playerId, 1);
		assertNotNull(move);
		assertNotNull(move.getJokenpo());
		assertNotNull(move.getPlayer());
		assertEquals(expected, this.moveService.listAllMove().size());
	}

	@Test(expected = NegocioException.class)
	public void registerDuplicateMoveSinglePlayer() throws Exception {
		Player player = new Player();
		playerRepository.save(player);
		this.registerManyPlayerSucesss();
		this.moveService.createMove(player.getId(), JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove(player.getId(), JokenpoEnum.PAPEL.getJokenpoId());
	}

	@Test
	public void registerSameMove() throws Exception {
		Long playerId = (long) 2;
		this.registerManyPlayerSucesss();
		this.moveService.createMove(playerId, 1);
		this.moveService.createMove(playerId, 1);
	}

	@Test
	public void registerMoveDifferentPlayersSucess() throws Exception {
		Long playerOne = (long) 2;
		Long playerTwo = (long) 3;
		Long playerTree = (long) 4;
		this.registerManyPlayerSucesss();
		this.moveService.createMove(playerOne, JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove(playerTwo, JokenpoEnum.PAPEL.getJokenpoId());
		this.moveService.createMove(playerTree, JokenpoEnum.TESOURA.getJokenpoId());
		assertEquals(3, this.moveService.listAllMove().size());
	}

	@Test
	public void deleteMoveSucess() throws Exception {
		Long playerOne = (long) 2;
		Long playerTwo = (long) 3;
		Long playerTree = (long) 4;
		this.registerManyPlayerSucesss();
		this.moveService.createMove(playerOne, JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove(playerTwo, JokenpoEnum.PAPEL.getJokenpoId());
		this.moveService.createMove(playerTree, JokenpoEnum.TESOURA.getJokenpoId());
		int beforeCounter = this.moveService.listAllMove().size();
		this.moveService.delete(playerOne);
		assertEquals(beforeCounter - 1, this.moveService.listAllMove().size());
	}

	private List<Player> insertDifferentPlayers(List<String> namePlayers) throws Exception {
		List<Player> list = new ArrayList<>();
		for (String name : namePlayers) {
			Player player = this.playerService.register(new Player(name));
			list.add(player);
		}
		return list;
	}

}
