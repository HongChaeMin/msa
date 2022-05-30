package com.example.userservice.controller;

import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Environment env;

    private final Greeting greeting;

    @GetMapping("/heath-check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String wellcome() {
        // return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

}
