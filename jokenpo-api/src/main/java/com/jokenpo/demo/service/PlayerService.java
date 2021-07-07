package com.jokenpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jokenpo.demo.exception.NegocioException;
import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	public Player register(Player player) throws NegocioException {
		if (player.getName() == null || player.getName().isEmpty()) {
			throw new NegocioException("Nome não pode ser vázio");
		}
		Optional<Player> playerExisting = playerRepository.findByName(player.getName());
		if (playerExisting.isPresent()) {
			throw new NegocioException("Já existe um jogador cadastrado com esse nome.");
		}

		return playerRepository.save(player);
	}

	public List<Player> listAll() {
		return (List<Player>) playerRepository.findAll();
	}
	
	public void delete(Long playerId) {
        playerRepository.deleteById(playerId);
	}
}
