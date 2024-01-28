package org.mvnsearch.chatgpt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name="QuestionAndAnswers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAndAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private String answer;

    private String tags;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    private LocalDateTime lastModified;

}
