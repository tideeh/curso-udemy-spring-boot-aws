package com.example.api.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.api.model.Person;
import com.example.api.vo.v1.PersonVO;
import com.example.api.vo.v2.PersonVOV2;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    @Mapping(source = "addres", target = "address")
    @Mapping(target = "birthday", ignore = true)
    Person personVOToPerson(PersonVO o);

    @Mapping(source = "address", target = "addres")
    @Mapping(target = "add", ignore = true)
    PersonVO personToPersonVO(Person o);

    List<PersonVO> personListToPersonVOList(List<Person> o);
    List<Person> personVOListToPersonList(List<PersonVO> o);

    // v2
    Person personVOV2ToPerson(PersonVOV2 o);

    @Mapping(target = "add", ignore = true)
    PersonVOV2 personToPersonVOV2(Person o);

    List<PersonVOV2> personListToPersonVOV2List(List<Person> o);
    List<Person> personVOV2ListToPersonList(List<PersonVOV2> o);

}
