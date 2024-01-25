package org.mvnsearch.chatgpt.controller;


import org.mvnsearch.chatgpt.GPTService.GPTService;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.completion.chat.ChatCompletionResponse;
import org.mvnsearch.chatgpt.spring.client.ChatGPTServiceProxyFactory;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1")
public class GPTController {
    @Autowired
    private ChatGPTService chatGPTService;

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
}