package org.mvnsearch.chatgpt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class GTPResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @JsonProperty("Question")
    @Column(name = "Question")
    private String question;
//    @JsonProperty("Answer")
    @Column(name = "Answer")
    private String answer;
//    @JsonProperty("Tags")
    @Column(name = "Tags")
    private String tags;


}
