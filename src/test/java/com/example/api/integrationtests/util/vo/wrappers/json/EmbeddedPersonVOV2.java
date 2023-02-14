package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.example.api.integrationtests.util.vo.v2.PersonVOV2;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmbeddedPersonVOV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("content")
    private List<PersonVOV2> content;

    public EmbeddedPersonVOV2() {}

    public List<PersonVOV2> getContent() {
        return this.content;
    }

    public void setContent(List<PersonVOV2> voV2List) {
        this.content = voV2List;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EmbeddedPersonVOV2)) {
            return false;
        }
        EmbeddedPersonVOV2 personEmbeddedVOV2 = (EmbeddedPersonVOV2) o;
        return Objects.equals(content, personEmbeddedVOV2.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
    
}
