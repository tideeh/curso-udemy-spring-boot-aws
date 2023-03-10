package com.example.api.unittests.util.mock;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.api.model.Person;
import com.example.api.util.vo.v1.PersonVO;
import com.example.api.util.vo.v2.PersonVOV2;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }

    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setAddress("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }
    
    public PersonVO mockVO() {
        return mockVO(0);
    }

    public PersonVO mockVO(Integer number) {
        PersonVO person = new PersonVO();
        person.setAddres("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }
    
    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonVO> mockVOList() {
        List<PersonVO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVO(i));
        }
        return persons;
    }

    public Person mockEntityV2() throws ParseException {
        return mockEntityV2(0);
    }

    public Person mockEntityV2(Integer number) throws ParseException {
        Person person = new Person();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        person.setBirthday(LocalDate.of(2000+number, 01, 25));
        person.setEnabled(((number % 2)==0) ? true : false);
        return person;
    }

    public PersonVOV2 mockVOV2() throws ParseException {
        return mockVOV2(0);
    }

    public PersonVOV2 mockVOV2(Integer number) throws ParseException {
        PersonVOV2 person = new PersonVOV2();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        person.setBirthday(LocalDate.of(2000+number, 01, 25));
        person.setEnabled(((number % 2)==0) ? true : false);
        return person;
    }

    public List<Person> mockEntityListV2() throws ParseException {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntityV2(i));
        }
        return persons;
    }

    public List<PersonVOV2> mockVOV2List() throws ParseException {
        List<PersonVOV2> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVOV2(i));
        }
        return persons;
    }

}
