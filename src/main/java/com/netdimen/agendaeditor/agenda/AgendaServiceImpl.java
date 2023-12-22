package com.netdimen.agendaeditor.agenda;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.List;

@Service
@Transactional
public class AgendaServiceImpl implements AgendaService{

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private AgendaItemRepository agendaItemRepository;

    @Override
    public Agenda createAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    @Override
    public Agenda getAgendaById(Long agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda not found with id: " + agendaId));
    }

    @Override
    public Agenda updateAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    @Override
    public void deleteAgenda(Long agendaId) {
        agendaRepository.deleteById(agendaId);
    }

    @Override
    public AgendaItem createAgendaItem(Long agendaId, AgendaItem agendaItem) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda not found with id: " + agendaId));

        agendaItem.setAgenda(agenda);
        return agendaItemRepository.save(agendaItem);
    }

    @Override
    public AgendaItem getAgendaItemById(Long agendaItemId) {
        return agendaItemRepository.findById(agendaItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AgendaItem not found with id: " + agendaItemId));
    }

    @Override
    public AgendaItem updateAgendaItem(AgendaItem agendaItem) {
        return agendaItemRepository.save(agendaItem);
    }

    @Override
    public void deleteAgendaItem(Long agendaItemId) {
        agendaItemRepository.deleteById(agendaItemId);
    }

    public Agenda saveAgendaWithItems(Agenda agenda, List<AgendaItem> agendaItems) {

        Agenda savedAgenda = agendaRepository.save(agenda);

        agendaItems.forEach(item -> item.setAgenda(savedAgenda));

        Iterable<AgendaItem> savedAgendaItems = agendaItemRepository.saveAll(agendaItems);

        savedAgenda.setAgendaItemList((List<AgendaItem>) savedAgendaItems);

        return savedAgenda;
    }

    @Override
    public Duration calculateTotalDuration(List<AgendaItem> agendaItems) {
        return agendaItems.stream()
                .map(AgendaItem::getDuration)
                .reduce(Duration::plus)  // Accumulate durations
                .orElse(Duration.ZERO);
    }

    @Override
    public long calculateTotalCreditableMinutes(Agenda agenda) {
        List<AgendaItem> agendaItems = agenda.getAgendaItemList();
        if (agendaItems != null) {
            return agendaItems.stream()
                    .filter(AgendaItem::isCreditable)
                    .mapToLong(AgendaItem::getDuration)
                    .sum();
        }
        return 0L;
    }

    @Override
    public boolean isTotalCreditableMinutesBelowThreshold(Agenda agenda) {
        return calculateTotalCreditableMinutes(agenda) < 15;
    }

}
