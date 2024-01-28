package org.mvnsearch.chatgpt.spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mvnsearch.chatgpt.dto.GPTResponse;
import org.mvnsearch.chatgpt.entity.GPTResponseTable;
import org.mvnsearch.chatgpt.entity.QuestionAndAnswers;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionResponse;
import org.mvnsearch.chatgpt.repository.GPTResponseRepository;
import org.mvnsearch.chatgpt.repository.QuestionAndAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class LinkParserService {

    public Long postId;

    @Autowired
    private ChatGPTService chatGPTService;

    @Autowired
    private GPTResponseRepository gptResponseRepository;

    @Autowired
    private QuestionAndAnswersRepository questionAndAnswersRepository;

    public LinkParserService(ChatGPTService chatGPTService, GPTResponseRepository gptResponseRepository, QuestionAndAnswersRepository questionAndAnswersRepository) {
        this.chatGPTService = chatGPTService;
        this.gptResponseRepository = gptResponseRepository;
        this.questionAndAnswersRepository = questionAndAnswersRepository;
    }

    public Mono<String> parseLinks(String content) {
        String[] linksToParse = new String[]{"https://netflixtechblog.com/rebuilding-netflix-video-processing-pipeline-with-microservices-4e5e6310e359", "https://www.linkedin.com/pulse/go-concurrency-series-introduction-goroutines-pratik-pandey-rjcme/", "https://netflixtechblog.com/all-of-netflixs-hdr-video-streaming-is-now-dynamically-optimized-e9e0cb15f2ba", "https://netflixtechblog.com/rebuilding-netflix-video-processing-pipeline-with-microservices-4e5e6310e359"};

        log.info("Parsing URL: " + linksToParse[0]);
        log.info("Start: " + LocalDateTime.now());

        String newcontent = content.concat(linksToParse[0]);
        var response =  chatGPTService.chat(ChatCompletionRequest.of(newcontent))
                .map(ChatCompletionResponse::getReplyText);


            response.subscribe(
                item -> {
                    // Success handler
                    System.out.println("Received: " + item);
                    var gptResponseList = parseResponse(item, linksToParse[0]);

                    System.out.println("printing questions while saving \n");
                    for( var row: gptResponseList) {
                        System.out.println("singleQuestion: " + row.getQuestion() + " " + row.getAnswer() + " " + row.getTags() + "\n");

                        // save in db
                        QuestionAndAnswers singleQuestion = questionAndAnswersRepository.save(
                                QuestionAndAnswers.builder()
                                        .question(row.getQuestion())
                                        .answer(row.getAnswer())
                                        .tags(row.getTags())
                                        .createdAt(LocalDateTime.now())
                                        .lastModified(LocalDateTime.now())
                                        .build());
                        assertThat(singleQuestion).isNotNull();
                    }
                },
                error -> {
                    // Error handler: process the error
                    System.err.println("Error occurred: " + error.getMessage());
                },
                () -> {
                    // Completion handler: called when the Mono completes
                    System.out.println("Completed");
        });


        return response;

    }

    private List<GPTResponse> parseResponse(String item, String url) {

        ObjectMapper objectMapper = new ObjectMapper();
        List<GPTResponse> gptResponse = null;
        try {
            gptResponse = objectMapper.readValue(item, new TypeReference<List<GPTResponse>>() {});

            GPTResponseTable entity = gptResponseRepository.save(GPTResponseTable.builder().link(url).data(item).createdAt(LocalDateTime.now()).lastModified(LocalDateTime.now()).build());
            postId = entity.getId();

            System.out.println("printing questions below after saving\n");
            gptResponse.forEach(r-> System.out.println(r.getQuestion() + " " + r.getAnswer() + " " + r.getTags() + "\n"));

        } catch (JsonProcessingException e) {
            log.error("JSON parsing error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return gptResponse;
    }


}

