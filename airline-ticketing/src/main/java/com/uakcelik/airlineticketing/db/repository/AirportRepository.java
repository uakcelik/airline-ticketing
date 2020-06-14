package com.uakcelik.airlineticketing.db.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uakcelik.airlineticketing.db.entity.Airport;

@Repository
public interface AirportRepository extends PagingAndSortingRepository<Airport, Integer> {
	
	@Query(value = "SELECT a FROM Airport a WHERE "
			+ "(:name is null or a.name = :name) "
			+ "and (:country is null or a.country = :country) "
			+ "and (:city is null or a.city = :city) "
			+ "and a.enabled = :enabled "
			+ "ORDER BY a.id")
	List<Airport> findAirportByNameAndCityAndCountryAndEnabledOrderById(@Param("name") String name, @Param("country") String country, @Param("city") String city, @Param("enabled") boolean enabled, Pageable pageable);
	
	@Query(value = "SELECT COUNT(*) FROM Airport a WHERE "
			+ "(:name is null or a.name = :name) "
			+ "and (:country is null or a.country = :country) "
			+ "and (:city is null or a.city = :city) "
			+ "and a.enabled = :enabled")
	int countAirportByNameAndCityAndCountryAndEnabled(@Param("name") String name, @Param("country") String country, @Param("city") String city, @Param("enabled") boolean enabled);
	
	Airport findByNameIgnoreCaseAndCountryIgnoreCaseAndCityIgnoreCaseAndEnabled(String name, String country, String city, boolean enabled);
	
	Airport findById(int id);
	
	Airport findByIdAndEnabled(int id, boolean enabled);
	
}
