package com.example.api.integrationtests.util.mock;

import java.time.LocalDate;

import com.example.api.integrationtests.util.vo.v1.PersonVO;
import com.example.api.integrationtests.util.vo.v2.PersonVOV2;

public class MockPerson {

    public static PersonVO mockVO() {
        PersonVO vo = new PersonVO();
        vo.setFirstName("Nelson");
		vo.setLastName("Piquet");
		vo.setAddres("Brasilia - DF");
		vo.setGender("Male");
        return vo;
    }

    public static PersonVOV2 mockVOV2() {
        PersonVOV2 vo = new PersonVOV2();
        vo.setFirstName("Leonardo");
		vo.setLastName("Di Caprio");
		vo.setAddress("Goiania - Goias");
		vo.setGender("Male");
		vo.setBirthday(LocalDate.of(1850, 06, 25));
        vo.setEnabled(true);
        return vo;
    }
    
}
