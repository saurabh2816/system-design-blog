package org.mvnsearch.chatgpt.controller;


import lombok.extern.slf4j.Slf4j;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionResponse;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.mvnsearch.chatgpt.spring.service.LinkParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1")
public class GPTController {
    @Autowired
    private ChatGPTService chatGPTService;

    @Autowired
    private LinkParserService  linkParserService;

    @GetMapping("/chat1")
    public String chat1() {
        return "saurabh";
    }

    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody String content) {
        return chatGPTService.chat(ChatCompletionRequest.of(content))
                .map(ChatCompletionResponse::getReplyText);
    }

    @GetMapping("/stream-chat")
    public Flux<String> streamChat(@RequestParam String content) {
        return chatGPTService.stream(ChatCompletionRequest.of(content))
                .map(ChatCompletionResponse::getReplyText);
    }


    @PostMapping("/parseLinks")
    public Mono<String> parseLinks(@RequestBody String content) {
        return linkParserService.parseLinks(content);
    }
}