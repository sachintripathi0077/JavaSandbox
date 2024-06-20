package com.eazybytes.eazyschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {
    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication){
        model.addAttribute("username",authentication.getName());
        model.addAttribute("roles",authentication.getAuthorities().toString());
        // for testing global exception controller
        throw new RuntimeException("It has benn a bad day!");
        //return "dashboard.html";
    }
}
