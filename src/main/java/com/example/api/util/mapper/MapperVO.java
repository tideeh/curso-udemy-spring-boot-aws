package com.example.api.util.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.api.model.Person;
import com.example.api.util.data.vo.v1.PersonVO;
import com.example.api.util.data.vo.v2.PersonVOV2;

@Mapper(componentModel = "spring")
public interface MapperVO {

    MapperVO INSTANCE = Mappers.getMapper( MapperVO.class );

    @Mapping(source = "addres", target = "address")
    Person personVOToPerson(PersonVO o);

    @Mapping(source = "address", target = "addres")
    PersonVO personToPersonVO(Person o);

    List<PersonVO> personListToPersonVOList(List<Person> o);
    List<Person> personVOListToPersonList(List<PersonVO> o);

    // v2
    default Person personVOV2ToPerson(PersonVOV2 o){
        Person entity = new Person();
        entity.setId(o.getId());
        entity.setAddress(o.getAddress());
        entity.setFirstName(o.getFirstName());
        entity.setLastName(o.getLastName());
        entity.setGender(o.getGender());

        return entity;
    }

    default PersonVOV2 personToPersonVOV2(Person o){
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(o.getId());
        vo.setAddress(o.getAddress());
        vo.setFirstName(o.getFirstName());
        vo.setLastName(o.getLastName());
        vo.setGender(o.getGender());
        vo.setBirthDay(new Date());

        return vo;
    }

    default List<PersonVOV2> personListToPersonVOV2List(List<Person> o){
        List<PersonVOV2> voList = new ArrayList<>();
        for (Person person : o) {
            PersonVOV2 vo = new PersonVOV2();
            vo.setId(person.getId());
            vo.setAddress(person.getAddress());
            vo.setFirstName(person.getFirstName());
            vo.setLastName(person.getLastName());
            vo.setGender(person.getGender());
            vo.setBirthDay(new Date());
            
            voList.add(vo);
        }

        return voList;
    }
}
