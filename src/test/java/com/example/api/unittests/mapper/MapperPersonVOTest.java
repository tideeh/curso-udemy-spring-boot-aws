package com.example.api.unittests.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.api.util.mapper.PersonMapper;
import com.example.api.util.vo.v1.PersonVO;
import com.example.api.util.vo.v2.PersonVOV2;
import com.example.api.model.Person;
import com.example.api.unittests.util.mock.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
public class MapperPersonVOTest {

    @Autowired
    PersonMapper mapper;
    
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
        mapper = PersonMapper.INSTANCE;
    }

    @Test
    public void personToVOTest() {
        PersonVO output = mapper.personToVO(inputObject.mockEntity());
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Addres Test0", output.getAddres());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void VOToPersonTest() {
        Person output = mapper.VOToPerson(inputObject.mockVO());
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Addres Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void personListToVOListTest() {
        List<PersonVO> outputList = mapper.personListToVOList(inputObject.mockEntityList());
        PersonVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Addres Test0", outputZero.getAddres());
        assertEquals("Male", outputZero.getGender());
        
        PersonVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Addres Test7", outputSeven.getAddres());
        assertEquals("Female", outputSeven.getGender());
        
        PersonVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Addres Test12", outputTwelve.getAddres());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void VOListToPersonListTest() {
        List<Person> outputList = mapper.VOListToPersonList(inputObject.mockVOList());
        Person outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Addres Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        
        Person outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Addres Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());
        
        Person outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Addres Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void personToVOV2Test() throws ParseException {
        PersonVOV2 output = mapper.personToVOV2(inputObject.mockEntityV2());
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
        assertTrue(output.getBirthday().isEqual(LocalDate.of(2000, 01, 25)));
    }

    @Test
    public void VOV2ToPersonTest() throws ParseException {
        Person output = mapper.VOV2ToPerson(inputObject.mockVOV2());
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
        assertTrue(output.getBirthday().isEqual(LocalDate.of(2000, 01, 25)));
    }

    @Test
    public void personListToVOV2ListTest() throws ParseException {
        List<PersonVOV2> outputList = mapper.personListToVOV2List(inputObject.mockEntityListV2());
        PersonVOV2 outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        assertTrue(outputZero.getBirthday().isEqual(LocalDate.of(2000, 01, 25)));
        
        PersonVOV2 outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());
        assertTrue(outputSeven.getBirthday().isEqual(LocalDate.of(2007, 01, 25)));
        
        PersonVOV2 outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
        assertTrue(outputTwelve.getBirthday().isEqual(LocalDate.of(2012, 01, 25)));
    }

    @Test
    public void VOV2ListToPersonListTest() throws ParseException {
        List<Person> outputList = mapper.VOV2ListToPersonList(inputObject.mockVOV2List());
        Person outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        assertTrue(outputZero.getBirthday().isEqual(LocalDate.of(2000, 01, 25)));
        
        Person outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());
        assertTrue(outputSeven.getBirthday().isEqual(LocalDate.of(2007, 01, 25)));
        
        Person outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
        assertTrue(outputTwelve.getBirthday().isEqual(LocalDate.of(2012, 01, 25)));
    }
}
