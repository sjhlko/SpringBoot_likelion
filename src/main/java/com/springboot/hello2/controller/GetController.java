package com.springboot.hello2.controller;

import com.springboot.hello2.domain.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")
@Slf4j
public class GetController {
    //get api
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        log.info("hello로 요청이 들어왔습니다");
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
        log.info("getvarialbe2로 요청이 들어옴 variable:{}",variable);
        return variable;
    }
    @GetMapping("/request1")
    public String getVariable2(@RequestParam String name, @RequestParam String email,
                               @RequestParam String organization){

        return String.format("%s %s %s",name,email,organization);
    }

    @GetMapping(value = "/request2")
    public String getVariable2(@RequestParam Map<String, String> param) {
        param.entrySet().forEach((map)->{
            System.out.printf("key:%s value:%s\n",map.getKey(),map.getValue());
        });
        return "request2가 호출 완료 되었습니다";
    }


    @GetMapping("/request3")
    public String getRequestParam(MemberDto memberDto){
        return memberDto.toString();

    }

}
