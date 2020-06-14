package com.uakcelik.airlineticketing.db.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uakcelik.airlineticketing.db.entity.Flight;

@Repository
public interface FlightRepository extends PagingAndSortingRepository<Flight, Integer> {

	
	@Query(value = "SELECT f FROM Flight f WHERE "
			+ "(:minPrice is null or f.price >= :minPrice) "
			+ "and (:maxPrice is null or f.price <= :maxPrice) "
			+ "and (:airlineId is null or f.airline.id = :airlineId) "
			+ "and (:routeId is null or f.route.id = :routeId) "
			+ "and f.enabled = :enabled "
			+ "ORDER BY f.id")
	List<Flight> findFlightByPriceAndAirlineAndRouteAndEnabledOrderById(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, @Param("airlineId") Integer airlineId, @Param("routeId") Integer routeId, @Param("enabled") boolean enabled, Pageable pageable);
	
	@Query(value = "SELECT COUNT(*) FROM Flight f WHERE "
			+ "(:minPrice is null or f.price >= :minPrice) "
			+ "and (:maxPrice is null or f.price <= :maxPrice) "
			+ "and (:airlineId is null or f.airline.id = :airlineId) "
			+ "and (:routeId is null or f.route.id = :routeId) "
			+ "and f.enabled = :enabled")
	int countFlightByPriceAndAirlineAndRouteAndEnabled(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, @Param("airlineId") Integer airlineId, @Param("routeId") Integer routeId, @Param("enabled") boolean enabled);
	
	Flight findById(int id);
	
	int countByAirlineIdAndEnabled(int airlineId, boolean enabled);
	
	int countByRouteIdAndEnabled(int routeId, boolean enabled);
	
	Flight findByIdAndEnabled(int id, boolean enabled);
	
}
