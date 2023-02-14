package com.example.api.integrationtests.util.vo.wrappers.json;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.example.api.integrationtests.util.vo.v1.BookVO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmbeddedBookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("books")
    private List<BookVO> voList;

    public EmbeddedBookVO() {}

    public List<BookVO> getVoList() {
        return this.voList;
    }

    public void setVoList(List<BookVO> voList) {
        this.voList = voList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EmbeddedBookVO)) {
            return false;
        }
        EmbeddedBookVO bookVOEmbedded = (EmbeddedBookVO) o;
        return Objects.equals(voList, bookVOEmbedded.voList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(voList);
    }

}
