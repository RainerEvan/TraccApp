package com.traccapp.demo.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.model.Accounts;
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

    int countByDeveloperAndIsActiveAndDateTakenBetween(Accounts developer, Boolean isActive, OffsetDateTime dateTakenStart, OffsetDateTime dateTakenEnd);
}
