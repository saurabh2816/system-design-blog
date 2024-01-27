package org.mvnsearch.chatgpt.service;


import org.mvnsearch.chatgpt.model.ChatCompletion;
import org.mvnsearch.chatgpt.model.Completion;
import org.mvnsearch.chatgpt.model.GPTExchange;
import reactor.core.publisher.Mono;

@GPTExchange
public interface GPTService {

//    @Bean
//    public GPTService gptHelloService(ChatGPTServiceProxyFactory proxyFactory) {
//        return proxyFactory.createClient(GPTService.class);
//    }

    @ChatCompletion("You are a language translator, please translate the below text to Chinese.\n")
    Mono<String> translateIntoChinese(String text);

    @ChatCompletion("You are a language translator, please translate the below text from {0} to {1}.\n {2}")
    Mono<String> translate(String sourceLanguage, String targetLanguage, String text);

    @Completion("please complete poem: {0}")
    Mono<String> completePoem(String text);

}

