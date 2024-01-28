package org.mvnsearch.chatgpt.repository;

import org.mvnsearch.chatgpt.entity.QuestionAndAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAndAnswersRepository extends JpaRepository<QuestionAndAnswers, Long> {
}