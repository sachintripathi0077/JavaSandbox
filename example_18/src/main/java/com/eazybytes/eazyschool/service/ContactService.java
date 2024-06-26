package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Contact savedContact = contactRepository.save(contact);
        if(null != savedContact && savedContact.getContactId()>0){
            isSaved = true;
        }
        return isSaved;
    }

    public List<Contact> findMessagesWithOpenStatus() {
        List<Contact> contactMessages = contactRepository.findByStatus(EazySchoolConstants.OPEN);
        return contactMessages;
    }

    public boolean updateMessageStatus(int contactId) {
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(EazySchoolConstants.CLOSE);
        });
        Contact updatedContact = contactRepository.save(contact.get());
        if(null != updatedContact && updatedContact.getUpdatedBy() != null){
            isUpdated = true;
        }

        return isUpdated;
    }

    public Page<Contact> findMessagesWithOpenStatus(int pageNum, String sortField, String sortDir) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum-1,
                pageSize,sortDir.equals("asc")? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        Page<Contact> messagePage = contactRepository.findByStatus(EazySchoolConstants.OPEN,pageable);
        return messagePage;
    }
}
