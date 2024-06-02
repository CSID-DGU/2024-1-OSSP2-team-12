package com.oos12.scansavvy.config;

import lombok.NoArgsConstructor;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

@Configuration
@NoArgsConstructor
public class OpenAiConfig {
    @Value("${spring.ai.openai.api-key}")
    private String openaiApiKey;
    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Bean
    public OpenAiApi openAiApi(){
        Assert.hasText(openaiApiKey, "Open API key must be set");
        Assert.hasText(baseUrl, "OpenAI base URL must be set");
        return new OpenAiApi(baseUrl, openaiApiKey);
    }
    @Bean
    public OpenAiChatClient openAiChatClient() {
        OpenAiApi openAiApi = new OpenAiApi(openaiApiKey);
        return new OpenAiChatClient(
                openAiApi, OpenAiChatOptions.builder()
                .withModel("gpt-3.5-turbo")
                .withTemperature(0.4f)
                .withMaxTokens(2000)
                .build()
        );
    }
    @Bean
    public OpenAiEmbeddingClient openAiEmbeddingClient() {
        OpenAiApi openAiApi = new OpenAiApi(baseUrl, openaiApiKey, RestClient.builder());
        return new OpenAiEmbeddingClient(
                openAiApi
        );
    }
    @Bean
    public OpenAiImageClient openAiImageClient() {
        // 필요한 설정을 사용하여 OpenAiImageClient 인스턴스를 생성합니다.
        OpenAiImageApi openAiImageApi = new OpenAiImageApi(baseUrl, openaiApiKey, RestClient.builder());
        // 다음은 OpenAiImageClient를 생성하고 반환합니다.
        return new OpenAiImageClient(openAiImageApi);
    }
    @Bean
    public FunctionCallbackContext springAiFunctionManager(ApplicationContext context){
        FunctionCallbackContext manager = new FunctionCallbackContext();
        manager.setApplicationContext(context);
        return manager;
    }
    @Bean
    public OpenAiAudioTranscriptionClient openAiAudioTranscriptionClient(){
        OpenAiAudioApi openAiAudioApi = new OpenAiAudioApi(openaiApiKey);
        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                // Add any required options
                .build();
        return new OpenAiAudioTranscriptionClient(openAiAudioApi, options);
    }
}
