package com.springboot.hello2.parser;

import com.springboot.hello2.dao.HospitalDao;
import com.springboot.hello2.domain.Hospital;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //없으면 nullpointerError 발생가능ㅅ
//SpringBoot가 스캔을 해서 등록한 Bean을 Test에서 쓸 수 있게 해줌
class HospitalParserTest {

    String line1 = "\"1\",\"의원\",\"01_01_02_P\",\"3620000\",\"PHMA119993620020041100004\",\"19990612\",\"\",\"01\",\"영업/정상\",\"13\",\"영업중\",\"\",\"\",\"\",\"\",\"062-515-2875\",\"\",\"500881\",\"광주광역시 북구 풍향동 565번지 4호 3층\",\"광주광역시 북구 동문대로 24, 3층 (풍향동)\",\"61205\",\"효치과의원\",\"20211115113642\",\"U\",\"2021-11-17 02:40:00.0\",\"치과의원\",\"192630.735112\",\"185314.617632\",\"치과의원\",\"1\",\"0\",\"0\",\"52.29\",\"401\",\"치과\",\"\",\"\",\"\",\"0\",\"0\",\"\",\"\",\"0\",\"\",";

    @Autowired //싱글톤을 위해 사용(계속해서 new를 해서 heap영역을 초과하는 것을 방지)
            //test에서 사용, 서비스코드는 final과 constructor를 씀->spring이 DI를 해줌
    ReadLineContext<Hospital> hospitalReadLineContext;

    @Autowired //HospitalDao는 Factory도 없는데 왜될까요,,?
            //@Component 달린 클래스는 다 bean으로 등록함
    HospitalDao hospitalDao;

    @Test
    @DisplayName("Hospital이 insert가 잘 되는지")
    void add(){
        HospitalParser hp = new HospitalParser();
        Hospital hospital = hp.parse(line1);
        hospitalDao.add(hospital);
    }

    @Test
    @DisplayName("count 테스트")
    void count(){
        assertEquals(1,hospitalDao.getCount());
    }

    @Test
    void name() throws IOException {
        //주소를 이렇게 놓으면 로컬에서만 돌아가게 됨
        //테스트코드란 어디서든지 작동하게 만들어야함
        String filename = "C:\\Users\\User\\Downloads\\fulldata_01_01_02_P_의원.csv";
        List<Hospital> hospitalList = hospitalReadLineContext.readByLine(filename);
        assertTrue(hospitalList.size()>1000);
        assertTrue(hospitalList.size()>10000);
        for (int i = 0; i < 10; i++) {
            System.out.println(hospitalList.get(i).getHospitalName());
        }

    }
    @Test
    @DisplayName("csv 한줄 읽어서 파싱")
    void convertToHospital(){
        HospitalParser hp = new HospitalParser();
        Hospital hospital = hp.parse(line1);

        assertEquals(1, hospital.getId()); // col:0
        assertEquals("의원", hospital.getOpenServiceName());//col:1
        assertEquals(3620000,hospital.getOpenLocalGovernmentCode()); // col: 3
        assertEquals("PHMA119993620020041100004",hospital.getManagementNumber()); // col:4
        assertEquals(LocalDateTime.of(1999, 6, 12, 0, 0, 0), hospital.getLicenseDate()); //19990612 //col:5
        assertEquals(1, hospital.getBusinessStatus()); //col:7
        assertEquals(13, hospital.getBusinessStatusCode());//col:9
        assertEquals("062-515-2875", hospital.getPhone());//col:15
        assertEquals("광주광역시 북구 풍향동 565번지 4호 3층", hospital.getFullAddress()); //col:18
        assertEquals("광주광역시 북구 동문대로 24, 3층 (풍향동)", hospital.getRoadNameAddress());//col:19
        assertEquals("효치과의원", hospital.getHospitalName());//col:21
        assertEquals("치과의원", hospital.getBusinessTypeName());//col:25
        assertEquals(1, hospital.getHealthcareProviderCount()); //col:29
        assertEquals(0, hospital.getPatientRoomCount()); //col:30
        assertEquals(0, hospital.getTotalNumberOfBeds()); //col:31
        assertEquals(52.29f, hospital.getTotalAreaSize()); //col:32
    }

}