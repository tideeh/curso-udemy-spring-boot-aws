package com.example.api.util.vo.v2;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//@JsonPropertyOrder({"id", "firstName", "lastName", "gender", "birthday", "addres"})
public class PersonVOV2 extends RepresentationModel<PersonVOV2> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    
    private String address;
    private Boolean enabled;

    public PersonVOV2() {}

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PersonVOV2)) {
            return false;
        }
        PersonVOV2 personVOV2 = (PersonVOV2) o;
        return Objects.equals(id, personVOV2.id) && Objects.equals(firstName, personVOV2.firstName) && Objects.equals(lastName, personVOV2.lastName) && Objects.equals(gender, personVOV2.gender) && Objects.equals(birthday, personVOV2.birthday) && Objects.equals(address, personVOV2.address) && Objects.equals(enabled, personVOV2.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, gender, birthday, address, enabled);
    }

}
