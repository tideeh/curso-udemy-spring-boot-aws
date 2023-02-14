package com.example.api.integrationtests.util.vo.wrappers.xmlyaml;

import java.util.List;

import com.example.api.integrationtests.util.vo.v1.PersonVO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperXmlYamlPersonVO {

    @XmlElement(name = "content")
    private List<PersonVO> content;

    public WrapperXmlYamlPersonVO() {}

    public List<PersonVO> getContent() {
        return this.content;
    }

    public void setContent(List<PersonVO> content) {
        this.content = content;
    }
    
}
