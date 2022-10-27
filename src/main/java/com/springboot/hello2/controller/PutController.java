package com.springboot.hello2.controller;

import com.springboot.hello2.domain.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/put-api")
public class PutController {

    @RequestMapping(value = "/domain",method = RequestMethod.PUT)
    public String putEx(){
        return "put example";
    }

    @PutMapping("/member1")
    public String putMember(@RequestBody Map<String,Object> postData){
        StringBuilder sb = new StringBuilder();
        postData.entrySet().forEach(map->sb.append(map.getKey()+":"+map.getValue()+"\n"));
        return sb.toString();
    }

    @PutMapping("/member2")
    public String putMember(@RequestBody MemberDto memberDto){
        return memberDto.toString();
    }

    @PutMapping("/member3")
    public ResponseEntity<MemberDto>
    putMember2(@RequestBody MemberDto memberDto){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(memberDto);

    }

}
