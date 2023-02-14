package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.example.api.integrationtests.util.vo.v2.PersonVOV2;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmbeddedPersonVOV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("content")
    private List<PersonVOV2> voV2List;

    public EmbeddedPersonVOV2() {}

    public List<PersonVOV2> getVoV2List() {
        return this.voV2List;
    }

    public void setVoV2List(List<PersonVOV2> voV2List) {
        this.voV2List = voV2List;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EmbeddedPersonVOV2)) {
            return false;
        }
        EmbeddedPersonVOV2 personEmbeddedVOV2 = (EmbeddedPersonVOV2) o;
        return Objects.equals(voV2List, personEmbeddedVOV2.voV2List);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(voV2List);
    }
    
}
