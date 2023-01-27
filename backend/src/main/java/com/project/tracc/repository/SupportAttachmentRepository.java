package com.project.tracc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.SupportAttachments;
import com.project.tracc.model.Supports;

@Repository
public interface SupportAttachmentRepository extends JpaRepository<SupportAttachments,UUID> {
    List<SupportAttachments> findAllBySupport(Supports support);
}
