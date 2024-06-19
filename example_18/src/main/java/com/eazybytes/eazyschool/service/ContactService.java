package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Service
@Slf4j
//@RequestScope : creates a new contact service bean everytime a contact form is submitted.
//@SessionScope : creates a new contact service bean for every new session. Observed that opening a new tab in the same browser won't create a new session. Only incognito or changing browser helps creating a new session.
//@ApplicationScope: creates a single contact service bean. 
public class ContactService {
    private int counter = 0;

    public ContactService(){
        System.out.println("ContactService bean initialized.");
    }

    public boolean saveMessageDetails(Contact contact){
        boolean  isSaved = true;
        log.info(contact.toString());
        return isSaved;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
