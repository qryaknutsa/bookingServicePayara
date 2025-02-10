package com.example.bookingServicePayara.dto;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "EventReadList")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({EventRead.class})
public class EventReadList implements Serializable {
    @XmlElement
    List<EventRead> list;

    public EventReadList(List<EventRead> list) {
        this.list = list;
    }

    public EventReadList() {
    }

    public List<EventRead> getList() {
        return list;
    }

}
