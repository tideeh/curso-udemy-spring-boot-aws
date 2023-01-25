package com.example.api.vo.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "firstName", "lastName", "gender", "birthday", "addres"})
public class PersonVOV2 extends RepresentationModel<PersonVOV2> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long key;

    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private String address;

    public PersonVOV2() {}

    public Long getKey() {
        return this.key;
    }

    public void setKey(Long key) {
        this.key = key;
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

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PersonVOV2)) {
            return false;
        }
        PersonVOV2 personVOv2 = (PersonVOV2) o;
        return Objects.equals(key, personVOv2.key) && Objects.equals(firstName, personVOv2.firstName) && Objects.equals(lastName, personVOv2.lastName) && Objects.equals(address, personVOv2.address) && Objects.equals(gender, personVOv2.gender) && Objects.equals(birthday, personVOv2.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, firstName, lastName, address, gender, birthday);
    }


    
}
