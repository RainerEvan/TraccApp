package com.traccapp.demo.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.model.TicketPK;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.payload.response.TopApplicationsResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, TicketPK> {
    Optional<Tickets> findByTicketId(UUID id);
    Optional<Tickets> findByTicketNo(String ticketNo);
    List<Tickets> findAllByReporter(Accounts reporter);

    boolean existsByDateAddedBetween(OffsetDateTime dateAddedStart, OffsetDateTime dateAddedEnd);
    
    int countByDateAddedBetween(OffsetDateTime dateAddedStart, OffsetDateTime dateAddedEnd);
    int countByStatusAndDateAddedBetween(Status status, OffsetDateTime dateAddedStart, OffsetDateTime dateAddedEnd);

    @Query(value="SELECT " +
                "    new com.traccapp.demo.payload.response.TopApplicationsResponse(t.application.name, COUNT(t.application.name)) " +
                "FROM " +
                "    Tickets AS t " +
                "WHERE " +
                "    t.dateAdded BETWEEN :dateAddedStart AND :dateAddedEnd " +
                "GROUP BY " +
                "    t.application.name")
    List<TopApplicationsResponse> countTicketByApplication(@Param("dateAddedStart") OffsetDateTime dateAddedStart, @Param("dateAddedEnd") OffsetDateTime dateAddedEnd);
}