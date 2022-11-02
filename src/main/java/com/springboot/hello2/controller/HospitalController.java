package com.springboot.hello2.controller;

import com.springboot.hello2.dao.HospitalDao;
import com.springboot.hello2.domain.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hospital")
public class HospitalController {

    @Autowired
    private HospitalDao hospitalDao;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Hospital> getHospitalInfo(@PathVariable int id){
        Hospital hospital = hospitalDao.findById(id);
        return ResponseEntity
                .ok()
                .body(hospital);
    }

}
