package com.pratap.superstore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class TestController {

    @GetMapping("welcome")
    public String getWelcome() {
        return "Welcome";
    }

}
