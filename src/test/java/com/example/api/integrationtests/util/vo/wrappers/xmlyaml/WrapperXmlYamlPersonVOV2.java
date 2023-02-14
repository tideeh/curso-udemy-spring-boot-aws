package com.example.api.integrationtests.util.vo.wrappers.xmlyaml;

import java.util.List;

import com.example.api.integrationtests.util.vo.v2.PersonVOV2;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperXmlYamlPersonVOV2 {

    @XmlElement(name = "content")
    private List<PersonVOV2> content;

    public WrapperXmlYamlPersonVOV2() {}

    public List<PersonVOV2> getContent() {
        return this.content;
    }

    public void setContent(List<PersonVOV2> content) {
        this.content = content;
    }
    
}
