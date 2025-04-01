package com.atividade1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atividade1.model.ProfessorModel;
import com.atividade1.service.ProfessorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("professor", professorService.findAll());
        return "professor/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professor", new ProfessorModel());
        return "professor/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid ProfessorModel professor, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "professor/form";
        }

        professorService.save(professor);
        attributes.addFlashAttribute("Mensagem", "Professor Salvo com Sucesso!");
        return "redirect:/professor";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("professor", professorService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor inválido: " + id)));
        return "professor/form";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable int id, RedirectAttributes attributes) {
        try {
            professorService.deleteById(id);
            attributes.addFlashAttribute("Mensagem", "Professor excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("Mensagem", "Erro ao excluir professor!");
        }
        return "redirect:/professores";
    }
}
