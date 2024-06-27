package com.eazyschool.consumer.rest_consumer.controller;

import com.eazyschool.consumer.rest_consumer.model.Contact;
import com.eazyschool.consumer.rest_consumer.model.Response;
import com.eazyschool.consumer.rest_consumer.proxy.ContactProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ContactController {
    @Autowired
    ContactProxy contactProxy;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    @GetMapping("/getMessages")
    public List<Contact> getMessages(@RequestParam("status") String status){
        return contactProxy.getMessagesByStatus(status);
    }

    @PostMapping("/saveMessage")
    public ResponseEntity<Response> saveMessage(@RequestBody Contact contact){
        String uri = "http://localhost:8080/api/contact/saveMessage";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("invocationFrom","RestTemplate");

        HttpEntity<Contact> httpEntity = new HttpEntity<>(contact,httpHeaders);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(uri, HttpMethod.POST,
                httpEntity, Response.class);

        return responseEntity;
    }

    @PostMapping("/saveMessageW")
    public Mono<Response> saveMessageW(@RequestBody Contact contact){
        String uri = "http://localhost:8080/api/contact/saveMessage";
        return webClient.post().uri(uri)
                .header("invocationFrom","WebClient")
                .body(Mono.just(contact), Contact.class)
                .retrieve()
                .bodyToMono(Response.class);
    }
}
