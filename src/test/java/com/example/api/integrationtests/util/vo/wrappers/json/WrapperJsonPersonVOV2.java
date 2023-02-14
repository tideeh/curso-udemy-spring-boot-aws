package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperJsonPersonVOV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private EmbeddedPersonVOV2 embedded;

    public WrapperJsonPersonVOV2() {}

    public EmbeddedPersonVOV2 getEmbedded() {
        return this.embedded;
    }

    public void setEmbedded(EmbeddedPersonVOV2 embedded) {
        this.embedded = embedded;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WrapperJsonPersonVOV2)) {
            return false;
        }
        WrapperJsonPersonVOV2 wrapperPersonVOV2 = (WrapperJsonPersonVOV2) o;
        return Objects.equals(embedded, wrapperPersonVOV2.embedded);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(embedded);
    }
    
}
