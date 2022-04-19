package com.example.secondservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/second-service")
@Log4j2
public class SecondServiceController {

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        log.info(request.getRequestURI());
        log.info("header > " + request.getHeader("second-request"));
        return "Welcome to the Second service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header){
        log.info("header > "+ header);
        return "ok-second";
    }

    @GetMapping("/custom-filter")
    public String check(HttpServletRequest request){
        log.info("Server port : {}", request.getServerPort());
        return "hi check second";
    }

}
