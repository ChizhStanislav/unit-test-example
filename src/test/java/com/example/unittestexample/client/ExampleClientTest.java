package com.example.unittestexample.client;


import com.example.unittestexample.config.RestTemplateConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@ExtendWith(SpringExtension.class)
@RestClientTest(ExampleClient.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes = {ExampleClient.class, MethodValidationPostProcessor.class, RestTemplateConfig.class})
class ExampleClientTest {

    @Autowired
    private ExampleClient exampleClient;

    @Autowired
    private RestTemplate template;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(template);
    }

    @Test
    void shouldReturnString_whenGetSomeInfo_givenExternalId() {
        // arrange
        mockServer
                .expect(requestTo("http://www.google.com/path?id=1"))
                .andExpect(method(GET))
                .andRespond(withSuccess("string", APPLICATION_JSON));

        // act
        var actual = exampleClient.getSomeInfo(1L);

        // assert
        assertEquals("string", actual);
    }
}