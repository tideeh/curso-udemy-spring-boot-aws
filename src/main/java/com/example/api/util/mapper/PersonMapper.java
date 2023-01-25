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

    @Mapping(source = "address", target = "addres")
    @Mapping(source = "id", target = "key")
    @Mapping(target = "add", ignore = true)
    PersonVO personToVO(Person o);

    @Mapping(source = "addres", target = "address")
    @Mapping(source = "key", target = "id")
    @Mapping(target = "birthday", ignore = true)
    Person VOToPerson(PersonVO o);

    @Mapping(source = "id", target = "key")
    List<PersonVO> personListToVOList(List<Person> o);

    @Mapping(source = "key", target = "id")
    List<Person> VOListToPersonList(List<PersonVO> o);

    // v2
    @Mapping(source = "id", target = "key")
    @Mapping(target = "add", ignore = true)
    PersonVOV2 personToVOV2(Person o);

    @Mapping(source = "key", target = "id")
    Person VOV2ToPerson(PersonVOV2 o);

    @Mapping(source = "id", target = "key")
    List<PersonVOV2> personListToVOV2List(List<Person> o);

    @Mapping(source = "key", target = "id")
    List<Person> VOV2ListToPersonList(List<PersonVOV2> o);
}
