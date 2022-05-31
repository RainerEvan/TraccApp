package com.traccapp.demo.repository;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.model.SupportAttachments;
import com.traccapp.demo.model.Supports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportAttachmentRepository extends JpaRepository<SupportAttachments,UUID> {
    List<SupportAttachments> findAllBySupport(Supports support);
}
