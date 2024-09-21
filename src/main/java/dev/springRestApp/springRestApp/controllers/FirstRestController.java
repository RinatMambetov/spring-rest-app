package dev.springRestApp.springRestApp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequestMapping("/api")
public class FirstRestController {

    @RequestMapping("/sayHello")
    public String sayHello() {
        return "Hello World!";
    }
}
