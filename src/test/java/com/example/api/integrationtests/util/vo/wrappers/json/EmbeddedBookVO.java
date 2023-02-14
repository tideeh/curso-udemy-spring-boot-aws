package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.example.api.integrationtests.util.vo.v1.BookVO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmbeddedBookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("content")
    private List<BookVO> content;

    public EmbeddedBookVO() {}

    public List<BookVO> getContent() {
        return this.content;
    }

    public void setContent(List<BookVO> voList) {
        this.content = voList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EmbeddedBookVO)) {
            return false;
        }
        EmbeddedBookVO bookVOEmbedded = (EmbeddedBookVO) o;
        return Objects.equals(content, bookVOEmbedded.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }

}
