package com.aimall.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringAIConfiguration {

    @Value("${spring.ai.otherai.base-url:}")
    private String otheraiBaseUrl;

    @Value("${spring.ai.otherai.api-key:}")
    private String otheraiApiKey;

    @Value("${spring.ai.otherai.embedding.model:}")
    private String otheraiEmbeddingModel;

    @Value("${spring.ai.otherai.embeddingsPath:}")
    private String otheraiEmbeddingsPath;

    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        Map<String, Object> extraBody = new HashMap<>();
        extraBody.put("enable_thinking", false);
        return ChatClient.builder(openAiChatModel).defaultOptions(OpenAiChatOptions.builder().extraBody(extraBody).build()).build();
    }

    @Primary
    @Bean
    public EmbeddingModel embeddingModel(OpenAiEmbeddingModel openAiEmbeddingModel) {
        return openAiEmbeddingModel;
    }


    //濡傛灉 浣跨敤鍏朵粬鐨勫悜閲忔ā鍨?姣斿璞嗗寘鐨勫氨寮€鍚笅闈㈣繖涓?璞嗗寘娌℃湁鎸夌収鏍囧噯鐨刼penai鏍囧噯,鎵€浠ラ渶瑕佽嚜瀹氫箟鍚戦噺妯″瀷瑙ｆ瀽
    /*
    @Bean
    @Primary
    public EmbeddingModel embeddingModel() {
        return new CustomMultimodalEmbeddingModel(otheraiBaseUrl, otheraiEmbeddingsPath,otheraiApiKey, otheraiEmbeddingModel);
    }
*/

    //鏈湴ollama 閰嶇疆
/*
    @Bean
    @Primary
    public ChatClient chatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel)
                .defaultOptions(OllamaChatOptions.builder().disableThinking().build())//绂佺敤鎬濊€?
                .build();
    }
*/

/*    @Bean
    @Primary
    public EmbeddingModel embeddingModel(OllamaEmbeddingModel ollamaEmbeddingModel) {
        return ollamaEmbeddingModel;
    }*/
}


