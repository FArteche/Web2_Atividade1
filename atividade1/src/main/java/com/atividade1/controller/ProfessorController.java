
package com.atividade1.controller;

import com.atividade1.model.Professor;
import com.atividade1.service.ProfessorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("professores", professorService.findAll());
        return "professor/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Professor professor, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "professor/form";
        }
        
        professorService.save(professor);
        attributes.addFlashAttribute("mensagem", "Professor salvo com sucesso!");
        return "redirect:/professores";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("professor", professorService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor inválido: " + id)));
        return "professor/form";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            professorService.deleteById(id);
            attributes.addFlashAttribute("mensagem", "Professor excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagem", "Erro ao excluir professor!");
        }
        return "redirect:/professores";
    }
}