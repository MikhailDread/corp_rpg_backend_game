package com.example.hrm_game.service.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpRequestService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders headers;

    /**
     * Функция создания тела запроса
     *
     * @param body - объект, добавляемый в тело
     * @return - сформированный запрос
     */
    public <T> HttpEntity<T> requestBody(T body) {
        return new HttpEntity<T>(body, headers);
    }

    /**
     * Функция отправки запроса по URL
     *
     * @param url        - url-адрес
     * @param httpMethod - метод запроса put, get, post и т.д.
     * @param httpEntity - запрос
     * @return - ответ от сервера
     */
    public <T> ResponseEntity<String> createRequest(String url, HttpMethod httpMethod, HttpEntity<T> httpEntity) {
        return restTemplate.exchange(
                url,
                httpMethod,
                httpEntity,
                String.class
        );
    }
}
