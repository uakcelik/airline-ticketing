package com.uakcelik.airlineticketing.db.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uakcelik.airlineticketing.db.entity.Ticket;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Integer> {
	
	@Query(value = "SELECT t FROM Ticket t WHERE "
			+ "(:ticketNumber is null or t.ticketNumber = :ticketNumber) "
			+ "and (:flightId is null or t.flight.id = :flightId) "
			+ "and t.enabled = :enabled "
			+ "ORDER BY t.id")
	List<Ticket> findTicketByTicketNumberAndFlightIdAndEnabledOrderById(@Param("ticketNumber") Integer ticketNumber, @Param("flightId") Integer flightId, @Param("enabled") boolean enabled, Pageable pageable);
	
	@Query(value = "SELECT COUNT(*) FROM Ticket t WHERE "
			+ "(:ticketNumber is null or t.ticketNumber = :ticketNumber) "
			+ "and (:flightId is null or t.flight.id = :flightId) "
			+ "and t.enabled = :enabled ")
	int countTicketByTicketNumberAndFlightIdAndEnabled(@Param("ticketNumber") Integer ticketNumber, @Param("flightId") Integer flightId, @Param("enabled") boolean enabled);
	
	Ticket findById(int id);
	
	Ticket findFirstByOrderByTicketNumberDesc();
	
	int countByFlightId(int flightId);
}
