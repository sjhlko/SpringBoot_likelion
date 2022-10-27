package com.springboot.hello2.controller;

import com.springboot.hello2.domain.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/post-api")
public class PostController {

    @RequestMapping(value = "/domain",method = RequestMethod.POST)
    public String postEx(){
        return "post example";
    }

    @PostMapping("/member1")
    public String postMember(@RequestBody Map<String,Object> postData){
        StringBuilder sb = new StringBuilder();
        postData.entrySet().forEach(map->sb.append(map.getKey()+":"+map.getValue()+"\n"));
        return sb.toString();
    }

    @PostMapping("/member2")
    public String postMember(@RequestBody MemberDto memberDto){
        return memberDto.toString();
    }
}
