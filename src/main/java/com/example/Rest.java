package com.example;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class Rest {

    private final WebClient webClient;

    public Rest(WebClient webClient) {
        this.webClient = webClient;
    }

    public String postRequest(String baseUrl, String url, String json, String token) {
        return webClient.mutate()
                .baseUrl(baseUrl)
                .build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getRequest(String baseUrl, String url, String token) {
        String param = "";
        return webClient.mutate()
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public static void main(String[] args) {
        Rest rest = new Rest(WebClient.builder().build());
        System.out.println("reqPost = " + rest.postRequest("http://localhost:8081", "/board2", "{}", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"));
        System.out.println("reqGet = " + rest.getRequest("http://localhost:8081", "/board", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"));
    }
}
