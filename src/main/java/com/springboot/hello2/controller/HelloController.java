package com.springboot.hello2.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/get-api")
public class HelloController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }
    @GetMapping("/name")
    public String getName(){
        return "heong";
    }
    @GetMapping("/variable1/{variable}")
    public String getVariable1(@PathVariable String variable){
        return variable;
    }
    @GetMapping("/variable2/{variable}")
    public String getVariable2(@PathVariable String variable){
        return variable;
    }
    
}
