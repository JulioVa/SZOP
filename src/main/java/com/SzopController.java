package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SzopController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/diagrams")
    public String diagrams() {
        return "diagrams";
    }

    @RequestMapping("/schema")
    public String schema() {
        return "schema";
    }

    @RequestMapping("/alerts")
    public String alerts() {
        return "alerts";
    }

    @RequestMapping("/sensors")
    public String sensors() {
        return "sensors";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "profile";
    }
}

