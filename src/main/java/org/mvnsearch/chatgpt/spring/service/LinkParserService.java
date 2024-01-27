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

    public Mono<String> parseLinks(String content, int times) {
        String[] linksToParse = new String[]{"https://www.linkedin.com/pulse/go-concurrency-series-introduction-goroutines-pratik-pandey-rjcme/", "https://netflixtechblog.com/all-of-netflixs-hdr-video-streaming-is-now-dynamically-optimized-e9e0cb15f2ba", "https://netflixtechblog.com/rebuilding-netflix-video-processing-pipeline-with-microservices-4e5e6310e359"};

        if(times <= 0) {
            return Mono.empty();
        }
        else {
            log.info("Parsing URL: " + linksToParse[times-1]);
            log.info("Start: " + LocalDateTime.now());

            String newcontent = content.concat(linksToParse[times-1]);
            return chatGPTService.chat(ChatCompletionRequest.of(newcontent))
                    .map(ChatCompletionResponse::getReplyText)
                    .doOnNext((r -> {
                        System.out.println("response: " + r);
                        log.info("End: " + LocalDateTime.now() + "\n\n\n");
                    }))
                    .delayElement(Duration.ofSeconds(20))
                    .then(parseLinks(content, times - 1));

        }
    }
}
