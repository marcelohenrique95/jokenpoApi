package com.jokenpo.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.jokenpo.demo.enums.JokenpoEnum;

@Entity
public class Move {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Player player;
	
	@Column
	private JokenpoEnum jokenpo;
	
	public Move() {
		
	}
	
	public Move(Player player, JokenpoEnum jokenpo) {
		this.player = player;
		this.jokenpo = jokenpo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public JokenpoEnum getJokenpo() {
		return jokenpo;
	}

	public void setJokenpo(JokenpoEnum jokenpo) {
		this.jokenpo = jokenpo;
	}

}
