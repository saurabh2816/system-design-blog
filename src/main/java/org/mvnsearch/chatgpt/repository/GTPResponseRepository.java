package org.mvnsearch.chatgpt.repository;

import org.mvnsearch.chatgpt.entity.GTPResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GTPResponseRepository extends JpaRepository<GTPResponse, Long> {
}