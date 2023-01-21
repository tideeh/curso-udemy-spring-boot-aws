package com.example.api.util.data.vo.v2;

import java.util.Date;
import java.util.Objects;

public class PersonVOV2 {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String addres;
    private String gender;
    private Date birthDay;

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

    public String getAddres() {
        return this.addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return this.birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PersonVOV2)) {
            return false;
        }
        PersonVOV2 personVOv2 = (PersonVOV2) o;
        return Objects.equals(id, personVOv2.id) && Objects.equals(firstName, personVOv2.firstName) && Objects.equals(lastName, personVOv2.lastName) && Objects.equals(addres, personVOv2.addres) && Objects.equals(gender, personVOv2.gender) && Objects.equals(birthDay, personVOv2.birthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, addres, gender, birthDay);
    }


    
}