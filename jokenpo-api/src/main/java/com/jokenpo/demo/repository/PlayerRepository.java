package com.jokenpo.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jokenpo.demo.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
	
	Optional<Player> findByName(String name);
	
	List<Player> findByNameContaining(String name);
	
	List<Player> deleteByName(String name);

}
