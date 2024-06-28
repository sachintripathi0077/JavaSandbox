package com.eazybytes.eazyschool.rest;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.model.Response;
import com.eazybytes.eazyschool.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;

@Slf4j
@RequestMapping(value = "/api/contact",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@RestController // @RestController = @Controller + @ResponseBody
@CrossOrigin(origins= "*")
public class ContactRestController {
    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping(value = "/getContactsByStatus", method = RequestMethod.GET)
    public List<Contact> getContactsByStatus(@RequestParam(name = "status") String status){
        List<Contact> contacts = contactRepository.findByStatus(status);
        return contacts;
    }

    @RequestMapping(value = "/getAllMessagesByStatus", method = RequestMethod.GET)
    public List<Contact> getAllMessagesByStatus(@RequestBody Contact contact){
        if(contact != null && (contact.getStatus() != null)){
            return contactRepository.findByStatus(contact.getStatus());
        } else {
            return List.of();
        }
    }

    @PostMapping("/saveMessage")
    public ResponseEntity<Response> saveMessage(@RequestHeader("invocationFrom") String invocationFrom,
                                                @Valid @RequestBody Contact contact){
        log.info(String.format("Header invocation form = %s",invocationFrom));
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message saved successfully.");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMessageSaved","true")
                .body(response);

    }

    @DeleteMapping("deleteMessage")
    public ResponseEntity<Response> deleteMessage(RequestEntity<Contact> requestEntity){
        HttpHeaders httpHeaders = requestEntity.getHeaders();
        httpHeaders.forEach((key,value)-> {
            log.info(String.format("Header '%s'= %s",key, value.stream().collect(Collectors.joining("|"))));
        });

        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Contact deleted successfully.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMessage")
    public ResponseEntity<Response> closeMessage(@RequestBody Contact contactReq){
        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());
        Response response = new Response();
        if(contact.isPresent()){
            contact.get().setStatus(EazySchoolConstants.CLOSE);
            contactRepository.save(contact.get());
            response.setStatusCode("200");
            response.setStatusMessage("Contact closed successfully");
        } else {
            response.setStatusCode("400");
            response.setStatusMessage("Invalid contact id received.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
