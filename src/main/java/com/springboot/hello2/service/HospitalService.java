package com.springboot.hello2.service;

import com.springboot.hello2.dao.HospitalDao;
import com.springboot.hello2.domain.Hospital;
import com.springboot.hello2.parser.ReadLineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    private final ReadLineContext<Hospital> hospitalReadLineContext;
    private final HospitalDao hospitalDao;

    public HospitalService(ReadLineContext<Hospital> hospitalReadLineContext, HospitalDao hospitalDao) {
        this.hospitalReadLineContext = hospitalReadLineContext;
        this.hospitalDao = hospitalDao;
    }

    //트랜잭션 이용시 시간이 너무 오래 걸림
//    @Transactional
//    public int insertLargeVolumeHospitalData(String filename){
//        int cnt = 0;
//        try {
//            List<Hospital> hospitalList = hospitalReadLineContext.readByLine(filename);
//            for(Hospital hospital: hospitalList){
//                try {
//                    this.hospitalDao.add(hospital);
//                    cnt++;
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return cnt;
//    }

    //멀티스레드 방식
    public int insertLargeVolumeHospitalData(String filename) {
        List<Hospital> hospitalList;
        try {
            hospitalList = hospitalReadLineContext.readByLine(filename);
            System.out.println("파싱이 끝났습니다.");
            hospitalList.stream()
                    .forEach(hospital -> {
                        try {
                            this.hospitalDao.add(hospital); // db에 insert하는 구간
                        } catch (Exception e) {
                            System.out.printf("id:%d 레코드에 문제가 있습니다.\n",hospital.getId());
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!Optional.of(hospitalList).isEmpty()) {
            return hospitalList.size();
        } else {
            return 0;
        }
    }
}
