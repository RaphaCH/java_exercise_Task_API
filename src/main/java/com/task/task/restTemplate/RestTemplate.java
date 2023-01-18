package com.task.task.restTemplate;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    public <T> Mono<T> get(String url, Class<T> responseType) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<List<T>> getAll(String url, Class<T> responseType) {
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<T>>() {
                });
    }

    public <T> Mono<T> post(String url, HttpEntity<?> request, Class<T> responseType) {
        return webClient
                .post()
                .uri(url)
                .bodyValue(request.getBody())
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> put(String url, HttpEntity<?> request, Class<T> responseType) {
        return webClient
                .put()
                .uri(url)
                .bodyValue(request.getBody())
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> delete(String url, Class<T> responseType) {
        return webClient
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }

    // public <T> Mono<T> handleError(Mono<T> mono, Class<T> responseType) {
    // return mono
    // .onErrorResume(error -> {
    // if (error instanceof WebClientResponseException) {
    // return
    // }
    // return Mono.error(error);
    // });
    // }
}