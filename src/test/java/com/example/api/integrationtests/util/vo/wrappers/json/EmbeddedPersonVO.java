package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.example.api.integrationtests.util.vo.v1.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmbeddedPersonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("content")
    private List<PersonVO> content;

    public EmbeddedPersonVO() {}

    public List<PersonVO> getContent() {
        return this.content;
    }

    public void setContent(List<PersonVO> voList) {
        this.content = voList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EmbeddedPersonVO)) {
            return false;
        }
        EmbeddedPersonVO personEmbeddedVO = (EmbeddedPersonVO) o;
        return Objects.equals(content, personEmbeddedVO.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }

}
