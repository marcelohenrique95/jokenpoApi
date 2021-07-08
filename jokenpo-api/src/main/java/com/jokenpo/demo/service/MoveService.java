package com.jokenpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jokenpo.demo.enums.JokenpoEnum;
import com.jokenpo.demo.exception.NegocioException;
import com.jokenpo.demo.model.Move;
import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.repository.MoveRepository;
import com.jokenpo.demo.repository.PlayerRepository;

@Service
public class MoveService {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private MoveRepository moveRepository;

	public Move createMove(Long playerId, int jokenpoId) throws NegocioException {
		Optional<Player> playerExisting = playerRepository.findById(playerId);

		if (!playerExisting.isPresent()) {
			throw new NegocioException("Não existe um jogador cadastrado com esse id.");
		}

		Optional<JokenpoEnum> jokenpoInput = JokenpoEnum.valueOf(jokenpoId);

		if (!jokenpoInput.isPresent()) {
			throw new NegocioException("Não existe uma opção com esse id.");
		}
		List<Move> moveExisting = moveRepository.findByPlayerId(playerId);

		if (moveExisting != null && !moveExisting.isEmpty()) {
			throw new NegocioException("Já existe uma jogada para esse jogador.");
		}

		Move move = new Move(playerExisting.get(), jokenpoInput.get());
		return moveRepository.save(move);
	}

	public List<Move> listAllMove() {
		return (List<Move>) moveRepository.findAll();
	}

	public void delete(Long moveId) {
		moveRepository.deleteById(moveId);
	}

	public void clearAll() {
		moveRepository.deleteAll();
	}
}
