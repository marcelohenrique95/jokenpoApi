package com.jokenpo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jokenpo.demo.model.Move;
import com.jokenpo.demo.service.MoveService;

@RestController
@RequestMapping("/v1/move")
public class MoveController {
	
	@Autowired
	private MoveService moveService;
	
	
	@PostMapping(path = "/{playerId}/{jokenpoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public Move registerMove(@PathVariable Long playerId,@PathVariable int jokenpoId) {
		return moveService.createMove(playerId, jokenpoId);
	}
	
	@GetMapping
	public List<Move> listAllMoves() {
		return moveService.listAllMove();
	}
	
	@DeleteMapping(path = "/{moveId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteMove(@PathVariable Long moveId) {
		 moveService.delete(moveId);
	}

}
