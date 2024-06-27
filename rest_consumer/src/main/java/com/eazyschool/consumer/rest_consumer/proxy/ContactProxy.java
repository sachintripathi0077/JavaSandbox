package com.eazyschool.consumer.rest_consumer.proxy;

import com.eazyschool.consumer.rest_consumer.config.ProjectConfiguration;
import com.eazyschool.consumer.rest_consumer.model.Contact;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "contact", url = "http://localhost:8080/api/contact",
        configuration = ProjectConfiguration.class)
public interface ContactProxy {
    @GetMapping("/getContactsByStatus")
    @Headers(value = "Content-Type: application/json")
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status);
}
