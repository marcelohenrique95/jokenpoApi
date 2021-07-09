package com.jokenpo.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jokenpo.demo.model.Move;

@Repository
public interface MoveRepository extends CrudRepository<Move, Long>{
	
	List<Move> findByPlayerName(String name);

}
