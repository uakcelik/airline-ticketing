package com.uakcelik.airlineticketing.db.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uakcelik.airlineticketing.db.entity.Route;

@Repository
public interface RouteRepository extends PagingAndSortingRepository<Route, Integer> {
	
	@Query(value = "SELECT r FROM Route r WHERE "
			+ "(:airportIdFrom is null or r.airportFrom.id = :airportIdFrom) "
			+ "and (:airportIdTo is null or r.airportTo.id = :airportIdTo) "
			+ "and r.enabled = :enabled "
			+ "ORDER BY r.id")
	List<Route> findRouteByAirportIdFromAndAirportIdToAndEnabledOrderById(@Param("airportIdFrom") Integer airportIdFrom, @Param("airportIdTo") Integer airportIdTo, @Param("enabled") boolean enabled, Pageable pageable);
	
	@Query(value = "SELECT COUNT(*) FROM Route r WHERE "
			+ "(:airportIdFrom is null or r.airportFrom.id = :airportIdFrom) "
			+ "and (:airportIdTo is null or r.airportTo.id = :airportIdTo) "
			+ "and r.enabled = :enabled")
	int countRouteByAirportIdFromAndAirportIdToAndEnabled(@Param("airportIdFrom") Integer airportIdFrom, @Param("airportIdTo") Integer airportIdTo, @Param("enabled") boolean enabled);
	
	Route findByAirportFromIdAndAirportToIdAndEnabled(int airportIdFrom, int airportIdTo, boolean enabled);
	
	Route findById(int id);
	
	@Query(value = "SELECT COUNT(*) FROM Route r WHERE "
			+ "(r.airportFrom.id = :airportId or r.airportTo.id = :airportId) "
			+ "and r.enabled = :enabled")
	int countRouteByAirportId(@Param("airportId") Integer airportId, @Param("enabled") boolean enabled);
	
	Route findByIdAndEnabled(int id, boolean enabled);
}
