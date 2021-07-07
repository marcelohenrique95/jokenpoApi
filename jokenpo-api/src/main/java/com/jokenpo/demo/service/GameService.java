package com.jokenpo.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jokenpo.demo.enums.JokenpoEnum;
import com.jokenpo.demo.exception.NegocioException;
import com.jokenpo.demo.model.Move;
import com.jokenpo.demo.repository.MoveRepository;
import com.jokenpo.demo.repository.PlayerRepository;

@Service
public class GameService {

	@Autowired
	private MoveRepository moveRepository;

	@Autowired
	private PlayerRepository playerRepository;

	public List<String> gaming() throws NegocioException {
		List<String> stringReturn = new ArrayList<String>();
		List<Move> moves = (List<Move>) moveRepository.findAll();

		if (moves == null || moves.size() <= 1) {
			throw new NegocioException("Não tem jogadas suficientes para a partida.");
		}

		List<Move> winners = new ArrayList<>();
		List<Move> losers = new ArrayList<>();
		moves.forEach(obj -> {
			try {
				if (whoWin(obj.getJokenpo().getLoseTo(), moves)) {
					winners.add(obj);
				} else {
					losers.add(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		deletePlayerLosers(losers, stringReturn);
		verifyPlayerWin(winners, stringReturn);

		return stringReturn;

	}

	private Boolean whoWin(List<JokenpoEnum> broke, List<Move> moves) throws Exception {
		for (JokenpoEnum enumJokenpo : broke) {
			for (Move mv : moves) {
				if (mv.getJokenpo().getJokenpoId() == enumJokenpo.getJokenpoId()) {
					return false;
				}
			}
		}
		return true;
	}

	private void deletePlayerLosers(List<Move> losers, List<String> stringReturn) {
		for (Move loser : losers) {
			playerRepository.deleteById(loser.getPlayer().getId());
			stringReturn.add("O Jogador " + loser.getPlayer().getId() + "-" + loser.getPlayer().getName()
					+ " perdeu e foi removido.");
		}
	}

	private void verifyPlayerWin(List<Move> winners, List<String> stringReturn) {
		for (Move winner : winners) {
			stringReturn
					.add("O jogador " + winner.getPlayer().getId() + "-" + winner.getPlayer().getName() + " venceu.");

		}
		switch (winners.size()) {
		case 0:
			stringReturn.add("Não houve vencedor");
			return;
		case 1:
			moveRepository.deleteAll();
			return;
		default:
			stringReturn
					.add("Não foi possível definir vencedor, jogue outra partida com os jogadores informados acima.");

		}

	}

}
