package com.eazybytes.eazyschool.rest;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping(value = "/api/contact")
@Controller
public class ContactRestController {
    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping(value = "/getContactsByStatus", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getContactsByStatus(@RequestParam(name = "status") String status){
        List<Contact> contacts = contactRepository.findByStatus(status);
        return contacts;
    }

    @RequestMapping(value = "/getAllMessagesByStatus", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllMessagesByStatus(@RequestBody Contact contact){
        if(contact != null && (contact.getStatus() != null)){
            return contactRepository.findByStatus(contact.getStatus());
        } else {
            return List.of();
        }
    }
}
