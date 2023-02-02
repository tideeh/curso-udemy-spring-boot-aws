package com.example.api.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.api.model.Person;
import com.example.api.util.vo.v1.PersonVO;
import com.example.api.util.vo.v2.PersonVOV2;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    @Mapping(source = "address", target = "addres")
    PersonVO personToVO(Person o);

    @Mapping(source = "addres", target = "address")
    @Mapping(target = "birthday", ignore = true)
    Person VOToPerson(PersonVO o);

    List<PersonVO> personListToVOList(List<Person> o);
    List<Person> VOListToPersonList(List<PersonVO> o);

    // v2
    PersonVOV2 personToVOV2(Person o);
    Person VOV2ToPerson(PersonVOV2 o);

    List<PersonVOV2> personListToVOV2List(List<Person> o);
    List<Person> VOV2ListToPersonList(List<PersonVOV2> o);
}
