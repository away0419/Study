package org.example;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Chat {
    public static void main(String[] args) {

        // .env 파일에서 open api key 가져오기
        String token = "";
        try (InputStream inputStream = Chat.class.getClassLoader().getResourceAsStream(".env");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
            token = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OpenAiService service = new OpenAiService(token);

        //보낼 메시지
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are a helpful assistant.");
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "effective java item 7 자세히 설명해줘");
        messages.add(systemMessage);
        messages.add(userMessage);
        
        // 요청 내용과 설정 만들기
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .logitBias(new HashMap<>())
                .build();

        // 서비스에 요청을 보내고 결과 값을 받아옴.
        ArrayList<String> result = new ArrayList<>();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(chunk -> {
                    String content = chunk.getChoices().get(0).getMessage().getContent();
                    result.add(content);
                });

        // 답변 하나로 묶고 프린트
        String answer = result.stream().filter(Objects::nonNull).collect(Collectors.joining(""));
        System.out.println(answer);
        service.shutdownExecutor();
    }
}