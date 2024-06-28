package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {
    @Autowired
    private PersonRepository personRepository;

    @Value("${eazyschool.pageSize}")
    private int defaultPageSize;

    @Value("${eazyschool.contact.successMessage}")
    private String successMessage;

    @Autowired
    private Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession httpSession){
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username",person.getName());
        model.addAttribute("roles",authentication.getAuthorities().toString());
        httpSession.setAttribute("loggedInPerson",person);

        if( null != person.getEazyClass() && (null != person.getEazyClass().getName())){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }
        // for testing global exception controller
        //throw new RuntimeException("It has benn a bad day!");
        logMessages();
        return "dashboard.html";
    }

    private void logMessages(){
        log.error("Error message from the dashboard controller.");
        log.warn("Warning message from the dashboard controller.");
        log.info("Info message from the dashboard controller.");
        log.debug("Debug message from the dashboard controller.");
        log.trace("Trace message from the dashboard controller.");

        // using @Value annotation
        log.error("defaultPageSize value via @Value annotation is: "+this.defaultPageSize);
        log.error("successMessage value via @Value annotation is: "+this.successMessage);

        // using Environment type variable
        log.error("defaultPageSize value via Environment annotation is: "+environment.getProperty("eazyschool.pageSize"));
        log.error("successMessage value via Environment annotation is: "+environment.getProperty("eazyschool.contact.successMessage"));
        log.error("JAVA_HOME : "+environment.getProperty("Path")); // Path is the system variable
    }
}
