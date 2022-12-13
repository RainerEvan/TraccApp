package com.traccapp.demo.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.payload.response.TopTagsResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<Supports, UUID>{
    List<Supports> findAllByTicketAndIsActive(Tickets ticket, Boolean isActive);
    List<Supports> findAllByDeveloper(Accounts developer);

    @Transactional
    List<Supports> findAllByDeveloperAndIsActiveAndDateTakenBetween(Accounts developer, Boolean isActive, OffsetDateTime dateTakenStart, OffsetDateTime dateTakenEnd);

    @Transactional
    @Query(value="SELECT " +
                "    (s) " +
                "FROM " +
                "    Supports AS s " +
                "LEFT JOIN " +
                "    s.ticket AS t " +
                "WHERE " +
                "    s.developer = :developer " +
                "AND " +
                "    s.isActive = :isActive " +
                "AND " +
                "    t.status = :status " +
                "AND " +
                "    s.dateTaken BETWEEN :dateTakenStart AND :dateTakenEnd"
                )
    List<Supports> findAllSupportByTicketStatus(@Param("developer") Accounts developer, @Param("isActive") Boolean isActive, @Param("status") Status status, @Param("dateTakenStart") OffsetDateTime dateTakenStart, @Param("dateTakenEnd") OffsetDateTime dateTakenEnd);
    
    @Query(value="SELECT " +
                "    new com.traccapp.demo.payload.response.TopTagsResponse(t.name, COUNT(s.id)) " +
                "FROM " +
                "    Supports AS s " +
                "RIGHT JOIN " +
                "    s.tags AS t " +
                // "LEFT JOIN " +
                // "    supports_tags AS st" +
                // "ON " +
                // "    s.id = st.supports_id" +
                // "LEFT JOIN " +
                // "    Tags AS t" +
                // "ON " +
                // "    t.id = st.tags_id" +
                "WHERE " +
                "    s.dateTaken BETWEEN :dateTakenStart AND :dateTakenEnd " +
                "GROUP BY " +
                "    t.name"
                )
    List<TopTagsResponse> countSupportByTag(@Param("dateTakenStart") OffsetDateTime dateTakenStart, @Param("dateTakenEnd") OffsetDateTime dateTakenEnd);

    @Query(value="SELECT COUNT " +
                "    (s) " +
                "FROM " +
                "    Supports AS s " +
                "LEFT JOIN " +
                "    s.ticket AS t " +
                "WHERE " +
                "    s.developer = :developer " +
                "AND " +
                "    s.isActive = :isActive " +
                "AND " +
                "    t.status = :status " +
                "AND " +
                "    s.dateTaken BETWEEN :dateTakenStart AND :dateTakenEnd"
                )
    int countSupportByTicketStatus(@Param("developer") Accounts developer, @Param("isActive") Boolean isActive, @Param("status") Status status, @Param("dateTakenStart") OffsetDateTime dateTakenStart, @Param("dateTakenEnd") OffsetDateTime dateTakenEnd);

    int countByDeveloperAndDateTakenBetween(Accounts developer, OffsetDateTime dateTakenStart, OffsetDateTime dateTakenEnd);

    int countByDeveloperAndIsActiveAndDateTakenBetween(Accounts developer, Boolean isActive, OffsetDateTime dateTakenStart, OffsetDateTime dateTakenEnd);
}
