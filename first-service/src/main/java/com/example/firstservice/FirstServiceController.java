package com.example.firstservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-service")
@Log4j2
public class FirstServiceController {

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        log.info(request.getRequestURI());
        log.info("header > " + request.getHeader("first-request"));
        return "Welcome to the First service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
        log.info("header > "+ header);
        return "ok-first";
    }

    @GetMapping("/custom-filter")
    public String check(){
        return "hi check first";
    }

}
