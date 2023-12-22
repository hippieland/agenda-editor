package com.netdimen.agendaeditor.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/agenda-items")
public class AgendaItemController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/create")
    public String showCreateAgendaItemForm(Model model) {
        model.addAttribute("agendaItem", new AgendaItem());
        return "createAgendaItem";
    }

    @PostMapping("/create")
    public String createAgendaItem(@ModelAttribute AgendaItem agendaItem) {
        AgendaItem createdAgendaItem = agendaService.createAgendaItem(agendaItem.getId(), agendaItem);
        return "redirect:/agenda-items/" + createdAgendaItem.getId();
    }

    @GetMapping("/{agendaItemId}/edit")
    public String showEditAgendaItemForm(@PathVariable Long agendaItemId, Model model) {
        AgendaItem agendaItem = agendaService.getAgendaItemById(agendaItemId);
        model.addAttribute("agendaItem", agendaItem);
        return "editAgendaItem";
    }

    @PostMapping("/{agendaItemId}/edit")
    public String editAgendaItem(@PathVariable Long agendaItemId, @ModelAttribute AgendaItem agendaItem) {
        if (!Objects.equals(agendaItemId, agendaItem.getId())) {
            return "redirect:/error";
        }

        AgendaItem updatedAgendaItem = agendaService.updateAgendaItem(agendaItem);
        return "redirect:/agenda-items/" + updatedAgendaItem.getId();
    }

    @PostMapping("/{agendaItemId}/delete")
    public String deleteAgendaItem(@PathVariable Long agendaItemId) {
        agendaService.deleteAgendaItem(agendaItemId);
        return "redirect:/agenda-items";
    }
}
