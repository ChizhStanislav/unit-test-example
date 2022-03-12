package com.example.unittestexample.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

@Component
@Validated
@RequiredArgsConstructor
public class ExampleClient {

    @Value("${some-external-service.url}")
    private String url;

    private final RestTemplate restTemplate;

    public String getSomeInfo(@NotNull Long externalId) {
        var uri =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host(url)
                        .path("/path")
                        .queryParam("id", externalId)
                        .build();

        return restTemplate.getForEntity(uri.toUri(), String.class).getBody();
    }
}
