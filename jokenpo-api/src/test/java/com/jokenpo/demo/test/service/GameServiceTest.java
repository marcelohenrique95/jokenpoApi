package com.jokenpo.demo.test.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jokenpo.demo.service.MoveService;
import com.jokenpo.demo.service.PlayerService;

public class GameServiceTest {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private MoveService moveService;

}
