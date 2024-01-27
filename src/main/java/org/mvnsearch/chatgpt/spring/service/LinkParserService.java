package org.mvnsearch.chatgpt.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class LinkParserService {

    @Autowired
    private ChatGPTService chatGPTService;

    public LinkParserService(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    public Mono<String> parseLinks(String content) {
        String[] linksToParse = new String[]{"https://www.linkedin.com/pulse/go-concurrency-series-introduction-goroutines-pratik-pandey-rjcme/", "https://netflixtechblog.com/all-of-netflixs-hdr-video-streaming-is-now-dynamically-optimized-e9e0cb15f2ba", "https://netflixtechblog.com/rebuilding-netflix-video-processing-pipeline-with-microservices-4e5e6310e359"};

        log.info("Parsing URL: " + linksToParse[0]);
        log.info("Start: " + LocalDateTime.now());

        String newcontent = content.concat(linksToParse[0]);
        var response =  chatGPTService.chat(ChatCompletionRequest.of(newcontent))
                .map(ChatCompletionResponse::getReplyText);

        response.subscribe(
            item -> {
                // Success handler: process the item
                System.out.println("Received: " + item);
            },
            error -> {
                // Error handler: process the error
                System.err.println("Error occurred: " + error.getMessage());
            },
            () -> {
                // Completion handler: called when the Mono completes
                System.out.println("Completed");
        });


        return Mono.empty();

//                .doOnNext((r -> {
//                    System.out.println("response: " + r);
//                    log.info("End: " + LocalDateTime.now() + "\n\n\n");
//                }))
//                .delayElement(Duration.ofSeconds(20));
    }
}

