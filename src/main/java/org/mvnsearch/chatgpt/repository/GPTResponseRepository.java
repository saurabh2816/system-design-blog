package org.mvnsearch.chatgpt.repository;

import org.mvnsearch.chatgpt.entity.GPTResponseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPTResponseRepository extends JpaRepository<GPTResponseTable, Long> {
}