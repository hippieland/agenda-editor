package com.netdimen.agendaeditor.agenda;

import java.time.Duration;
import java.util.List;

public interface AgendaService {
    Agenda createAgenda(Agenda agenda);

    Agenda getAgendaById(Long agendaId);

    Agenda updateAgenda(Agenda agenda);

    void deleteAgenda(Long agendaId);

    AgendaItem createAgendaItem(Long agendaId, AgendaItem agendaItem);

    AgendaItem getAgendaItemById(Long agendaItemId);

    AgendaItem updateAgendaItem(AgendaItem agendaItem);

    void deleteAgendaItem(Long agendaItemId);

    Agenda saveAgendaWithItems(Agenda agenda, List<AgendaItem> agendaItems);

    public Duration calculateTotalDuration(List<AgendaItem> agendaItems) ;

    long calculateTotalCreditableMinutes(Agenda agenda);

    boolean isTotalCreditableMinutesBelowThreshold(Agenda agenda);
}
