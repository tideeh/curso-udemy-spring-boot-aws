package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperJsonBookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private EmbeddedBookVO embedded;

    public WrapperJsonBookVO() {}

    public EmbeddedBookVO getEmbedded() {
        return this.embedded;
    }

    public void setEmbedded(EmbeddedBookVO embedded) {
        this.embedded = embedded;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WrapperJsonBookVO)) {
            return false;
        }
        WrapperJsonBookVO wrapperJsonBookVO = (WrapperJsonBookVO) o;
        return Objects.equals(embedded, wrapperJsonBookVO.embedded);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(embedded);
    }
    
}
