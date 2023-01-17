package com.project.tracc.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Accounts;
import com.project.tracc.model.Status;
import com.project.tracc.model.TicketPK;
import com.project.tracc.model.Tickets;
import com.project.tracc.payload.response.TopApplicationsResponse;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, TicketPK> {
    Optional<Tickets> findByTicketId(UUID id);
    Optional<Tickets> findByTicketNo(String ticketNo);
    List<Tickets> findAllByReporter(Accounts reporter);

    boolean existsByDateAddedBetween(OffsetDateTime dateAddedStart, OffsetDateTime dateAddedEnd);
    
    int countByDateAddedBetween(OffsetDateTime dateAddedStart, OffsetDateTime dateAddedEnd);
    int countByStatusAndDateAddedBetween(Status status, OffsetDateTime dateAddedStart, OffsetDateTime dateAddedEnd);

    @Query(value="SELECT " +
                "    new com.project.tracc.payload.response.TopApplicationsResponse(t.application.name, COUNT(t.application.name)) " +
                "FROM " +
                "    Tickets AS t " +
                "WHERE " +
                "    t.dateAdded BETWEEN :dateAddedStart AND :dateAddedEnd " +
                "GROUP BY " +
                "    t.application.name")
    List<TopApplicationsResponse> countTicketByApplication(@Param("dateAddedStart") OffsetDateTime dateAddedStart, @Param("dateAddedEnd") OffsetDateTime dateAddedEnd);
}