package com.uakcelik.airlineticketing.db.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.uakcelik.airlineticketing.db.entity.Airline;

@Repository
public interface AirlineRepository extends PagingAndSortingRepository<Airline, Integer> {

	List<Airline> findByEnabledOrderById(boolean enabled, Pageable pageable);
	int countByEnabled(boolean enabled);
	
	List<Airline> findByEnabledAndNameIgnoreCaseContainingOrderById(boolean enabled, String name, Pageable pageable);
	int countByEnabledAndNameIgnoreCaseContaining(boolean enabled, String name);
	
	Airline findByNameIgnoreCaseAndEnabled(String name, boolean enabled);
	
	Airline findById(int id);
	
	Airline findByIdAndEnabled(int id, boolean enabled);
	
}
