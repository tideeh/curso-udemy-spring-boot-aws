package com.example.api.integrationtests.util.vo.wrappers.xmlyaml;

import java.util.List;

import com.example.api.integrationtests.util.vo.v1.BookVO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperXmlYamlBookVO {

    @XmlElement(name = "content")
    private List<BookVO> content;

    public WrapperXmlYamlBookVO() {}

    public List<BookVO> getContent() {
        return this.content;
    }

    public void setContent(List<BookVO> content) {
        this.content = content;
    }
    
}
