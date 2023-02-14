package com.task.task.restTemplate;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class RestTemplate {

    private final WebClient webClient;

    public RestTemplate() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    // So far only added .onStatus on this method to show it to you
    // if ok, should be added to all methods
    public <T> Mono<ResponseEntity<T>> getOne(String url, Class<T> responseType) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> Mono.empty())
                .toEntity(responseType);
    }
    
    public <T> Mono<ResponseEntity<List<T>>> getAll(String url, Class<T> responseType) {
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(responseType);
                // .bodyToMono(new ParameterizedTypeReference<List<T>>() {
                // });
    }

    public <T> Mono<ResponseEntity<T>> post(String url, HttpEntity<?> request, Class<T> responseType) {
        return webClient
                .post()
                .uri(url)
                .bodyValue(request.getBody())
                .retrieve()
                .toEntity(responseType);
                // .bodyToMono(responseType);
    }

    public <T> Mono<ResponseEntity<T>> put(String url, HttpEntity<?> request, Class<T> responseType) {
        return webClient
                .put()
                .uri(url)
                .bodyValue(request.getBody())
                .retrieve()
                .toEntity(responseType);
                // .bodyToMono(responseType);
    }

    public <T> Mono<ResponseEntity<T>> delete(String url, Class<T> responseType) {
        return webClient
                .delete()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> Mono.empty())
                .toEntity(responseType);
                // .bodyToMono(responseType);
    }

}