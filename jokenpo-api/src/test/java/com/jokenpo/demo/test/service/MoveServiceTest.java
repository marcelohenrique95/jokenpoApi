package com.jokenpo.demo.test.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
@Transactional
public class MoveServiceTest {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MoveService moveService;
	
	@Autowired
	PlayerRepository playerRepository;
	
	
	
	Executable closureContainingCodeToTest = () -> {throw new NegocioException("msg");};

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
		this.playerService.clearAll();
		this.registerManyPlayerSucesss();
		int expected = 1;
		Move move = this.moveService.createMove("P1", 1);
		assertNotNull(move);
		assertNotNull(move.getJokenpo());
		assertNotNull(move.getPlayer());
		assertEquals(expected, this.moveService.listAllMove().size());
	}

	//@Test
	public void registerDuplicateMoveSinglePlayer() throws Exception {
		this.registerManyPlayerSucesss();
		this.moveService.createMove("P1", JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove("P1", JokenpoEnum.PAPEL.getJokenpoId());
		assertThrows(NegocioException.class, closureContainingCodeToTest);
	}

	@Test
	public void registerSameMove() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();
		this.registerManyPlayerSucesss();
		this.moveService.createMove("P1", JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.PAPEL.getJokenpoId());
	}

	@Test
	public void registerMoveDifferentPlayersSucess() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();
		this.registerManyPlayerSucesss();
		this.moveService.createMove("P1", JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.PAPEL.getJokenpoId());
		this.moveService.createMove("P3", JokenpoEnum.TESOURA.getJokenpoId());
		assertEquals(3, this.moveService.listAllMove().size());
	}

	@Test
	public void deleteMoveSucess() throws Exception {
		this.playerService.clearAll();
		this.moveService.clearAll();
		List<String> namePlayers = new ArrayList<>(asList("P1", "P2", "P3", "P4", "P5", "P6"));
		List<Player> player = this.insertDifferentPlayers(namePlayers);
		this.registerManyPlayerSucesss();
		this.moveService.createMove("P1", JokenpoEnum.PEDRA.getJokenpoId());
		this.moveService.createMove("P2", JokenpoEnum.PAPEL.getJokenpoId());
		this.moveService.createMove("P3", JokenpoEnum.TESOURA.getJokenpoId());
		int beforeCounter = this.moveService.listAllMove().size();
//moverepository
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