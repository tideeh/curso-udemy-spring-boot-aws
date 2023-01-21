package com.example.api.util.mapper;

import java.util.Date;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.api.model.Person;
import com.example.api.util.data.vo.v1.PersonVO;
import com.example.api.util.data.vo.v2.PersonVOV2;

@Mapper(componentModel = "spring")
public interface MapperVO {

    MapperVO INSTANCE = Mappers.getMapper( MapperVO.class );

    Person personVOToPerson(PersonVO o);
    PersonVO personToPersonVO(Person o);
    List<PersonVO> personListToPersonVOList(List<Person> o);
    List<Person> personVOListToPersonList(List<PersonVO> o);

    // v2
    default Person personVOV2ToPerson(PersonVOV2 o){
        Person entity = new Person();
        entity.setId(o.getId());
        entity.setAddres(o.getAddres());
        entity.setFirstName(o.getFirstName());
        entity.setLastName(o.getLastName());
        entity.setGender(o.getGender());

        return entity;
    }

    default PersonVOV2 personToPersonVOV2(Person o){
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(o.getId());
        vo.setAddres(o.getAddres());
        vo.setFirstName(o.getFirstName());
        vo.setLastName(o.getLastName());
        vo.setGender(o.getGender());
        vo.setBirthDay(new Date());

        return vo;
    }
}
