package com.netdimen.agendaeditor.agenda;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "agenda")
    private List<AgendaItem> agendaItemList = new ArrayList<>();

    Agenda() {

    }

    public Agenda(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AgendaItem> getAgendaItemList() {
        return agendaItemList;
    }

    public void setAgendaItemList(List<AgendaItem> agendaItemList) {
        this.agendaItemList = agendaItemList;
    }
}
