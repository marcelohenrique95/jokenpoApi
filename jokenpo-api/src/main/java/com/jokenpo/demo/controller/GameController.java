package com.jokenpo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jokenpo.demo.service.GameService;

@RestController
@RequestMapping("/v1/game")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping
	public List<String> resultGame() {
		return gameService.gaming();
	}

}
