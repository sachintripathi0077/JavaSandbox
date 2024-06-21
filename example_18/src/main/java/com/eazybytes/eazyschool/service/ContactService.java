package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
//@RequestScope : creates a new contact service bean everytime a contact form is submitted.
//@SessionScope : creates a new contact service bean for every new session. Observed that opening a new tab in the same browser won't create a new session. Only incognito or changing browser helps creating a new session.
//@ApplicationScope: creates a single contact service bean.
public class ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    public ContactService(){
        System.out.println("ContactService bean initialized.");
    }

    public boolean saveMessageDetails(Contact contact){
        boolean  isSaved = false;
        contact.setStatus(EazySchoolConstants.OPEN);
        contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        int result = contactRepository.saveContactMessage(contact);
        System.out.println("__saveMessageDetails: ContactService:: result: "+result);
        if(result>0){
            isSaved = true;
        }
        return isSaved;
    }

    public List<Contact> findMessagesWithOpenStatus() {
        List<Contact> contactMessages = contactRepository.findMessagesWithStatus(EazySchoolConstants.OPEN);
        return contactMessages;
    }

    public boolean updateMessageStatus(int contactId, String updateBy) {
        int result = contactRepository.updateMessageStatus(contactId,EazySchoolConstants.CLOSE,updateBy);
        if(result>0)
            return true;
        return false;
    }
}
