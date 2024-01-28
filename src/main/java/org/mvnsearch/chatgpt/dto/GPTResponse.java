package org.mvnsearch.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GPTResponse {
    @JsonProperty("Question")
    private String question;
    @JsonProperty("Answer")
    private String answer;
    @JsonProperty("Tags")
    private String tags;
}
