package com.jokenpo.demo.test.service;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.repository.PlayerRepository;
import com.jokenpo.demo.service.PlayerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PlayerServiceTest {

	@Autowired
	PlayerService playerService;

	@Autowired
	PlayerRepository playerRepository;

	@Test
	public void insertPlayerSucess() {
		String onePlayer = "PlayerOne";
		Player player = this.playerService.register(new Player(onePlayer));
		assertEquals(onePlayer, player.getName());
	}

	@Test
	public void getPlayerSucess() throws Exception {
		this.playerService.clearAll();
		List<String> namePlayers = (asList("P1", "P2", "P3"));
		List<Player> player = this.registerManyPlayers(namePlayers);
		assertEquals(namePlayers.size(), player.size());
        assertEquals(namePlayers.size(), this.playerService.listAll().size());
        assertEquals(player.size(), this.playerService.listAll().size());
	}

	@Test
	public void duplicateRegisterPlayerException() throws Exception {
		String playerDuplicate = "NAME";
		this.playerService.register(new Player(playerDuplicate));
		this.playerService.register(new Player(playerDuplicate));
	}

	@Test
	public void getPlayerByName() throws Exception {
		this.playerService.clearAll();
		List<String> namePlayers = new ArrayList<String>(asList("P1", "P2", "P3"));
		List<Player> player = this.registerManyPlayers(namePlayers);
		Optional<Player> playerSearch = this.playerRepository.findByName("P2");
		assertEquals("P2", playerSearch.get().getName());
	}

	@Test
	public void getPlayerNameException() throws Exception {
		this.playerService.clearAll();
		List<String> namePlayers = new ArrayList<String>(asList("P1", "P2", "P3"));
		this.registerManyPlayers(namePlayers);
		this.playerRepository.findByName("OTHER");
	}

	@Test
	public void deletePlayerByName() throws Exception {
		this.playerService.clearAll();
		List<String> namePlayers = new ArrayList<String>(asList("P1", "P2", "P3"));
		List<Player> player = this.registerManyPlayers(namePlayers);
		int expected1 = namePlayers.size() - 1;
		int expected2 = namePlayers.size() - 1;
		List<Player> list = this.playerRepository.deleteByName("P1");
		assertEquals(expected1, list.size());
		assertEquals(expected2, list.size());
	}

	@Test
	public void deleteByNameException() throws Exception {
		this.playerService.clearAll();
		List<String> namePlayers = new ArrayList<>(asList("P1", "P2", "P3", "P4", "P5"));
		this.registerManyPlayers(namePlayers);
		this.playerRepository.deleteByName("OTHER");
	}

	@Test
	public List<Player> registerManyPlayers(List<String> namePlayers) throws Exception {
		List<Player> list = new ArrayList<>();
		for (String name : namePlayers) {
			Player player = this.playerService.register(new Player(name));
			list.add(player);
		}
		return list;
	}

}
