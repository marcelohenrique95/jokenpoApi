package com.jokenpo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jokenpo.demo.model.Player;
import com.jokenpo.demo.service.PlayerService;

@RestController
@RequestMapping("/v1/player")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Player registerPlayer(@RequestBody Player player) {
		return playerService.register(player);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Player> listAllPlayer() {
		return playerService.listAll();
	}
	
	@DeleteMapping(path = "/{playerId}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePlayer(@PathVariable Long playerId) {
		playerService.delete(playerId);
	}

}
