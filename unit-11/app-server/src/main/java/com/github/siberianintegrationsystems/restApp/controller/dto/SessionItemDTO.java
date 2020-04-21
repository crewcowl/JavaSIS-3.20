package com.github.siberianintegrationsystems.restApp.controller.dto;

import com.github.siberianintegrationsystems.restApp.entity.Session;

import java.util.Date;

public class SessionItemDTO extends JournalItemDTO {

    public String name;
    public Date insertDate;
    public Double result;

    public SessionItemDTO () {

    }

    public SessionItemDTO(Session session) {
        this.id = session.getId().toString();
        this.name = session.getFio();
        this.insertDate = session.getDate();
        this.result = session.getPercent();
    }
}
