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
	
	public Move createMove(String playerName, int jokenpoId) throws NegocioException {
		Optional<Player>playerExisting = playerRepository.findByName(playerName);
		
		
		
		if(!playerExisting.isPresent()) {
			throw new NegocioException("N�o existe um jogador cadastrado com esse nome.");
		}
		
		Optional<JokenpoEnum> jokenpoInput = JokenpoEnum.valueOf(jokenpoId);
		
		if(!jokenpoInput.isPresent()) {
			throw new NegocioException("N�o existe uma jogada com esse id. Tente 1,2 ou 3");
		}
		List<Move>moveExisting = moveRepository.findByPlayerName(playerName);
		
		if(moveExisting != null && !moveExisting.isEmpty()) {
			throw new NegocioException("J� existe uma jogada para esse jogador.");
		}
		
		Move move = new Move(playerExisting.get(), jokenpoInput.get());
		return moveRepository.save(move);
	}
	
	public List<Move> listAllMove(){
		return (List<Move>) moveRepository.findAll();
	}
	
	public void delete(Long moveId) {
        moveRepository.deleteById(moveId);
	}
	
	public void clearAll() {
		moveRepository.deleteAll();
	}

}
