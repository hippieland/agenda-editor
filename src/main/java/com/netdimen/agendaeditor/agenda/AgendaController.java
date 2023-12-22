package com.netdimen.agendaeditor.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    private AgendaRepository agendaRepository;

    public AgendaController(AgendaRepository agendaRepository) {

        this.agendaRepository = agendaRepository;
    }

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/create")
    public String showCreateAgendaForm(Model model) {
        model.addAttribute("agenda", new Agenda());
        return "createAgenda";
    }

    @PostMapping("/create")
    public String createAgenda(@ModelAttribute Agenda agenda) {
        Agenda createdAgenda = agendaService.createAgenda(agenda);
        return "redirect:/agendas/" + createdAgenda.getId();
    }

    @GetMapping("/{agendaId}/edit")
    public String showEditAgendaForm(@PathVariable Long agendaId, Model model) {
        Agenda agenda = agendaService.getAgendaById(agendaId);
        model.addAttribute("agenda", agenda);
        return "editAgenda";
    }

    @PostMapping("/{agendaId}/edit")
    public String editAgenda(@PathVariable Long agendaId, @ModelAttribute Agenda agenda) {
        if (!Objects.equals(agendaId, agenda.getId())) {
            return "redirect:/error";
        }

        Agenda updatedAgenda = agendaService.updateAgenda(agenda);
        return "redirect:/agendas/" + updatedAgenda.getId();
    }

    @PostMapping("/{agendaId}/delete")
    public String deleteAgenda(@PathVariable Long agendaId) {
        agendaService.deleteAgenda(agendaId);
        return "redirect:/agendas";
    }

}
