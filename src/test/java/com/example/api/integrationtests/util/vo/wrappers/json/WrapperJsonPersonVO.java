package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperJsonPersonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private EmbeddedPersonVO embedded;

    public WrapperJsonPersonVO() {}

    public EmbeddedPersonVO getEmbedded() {
        return this.embedded;
    }

    public void setEmbedded(EmbeddedPersonVO embedded) {
        this.embedded = embedded;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WrapperJsonPersonVO)) {
            return false;
        }
        WrapperJsonPersonVO wrapperPersonVO = (WrapperJsonPersonVO) o;
        return Objects.equals(embedded, wrapperPersonVO.embedded);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(embedded);
    }
    
}
