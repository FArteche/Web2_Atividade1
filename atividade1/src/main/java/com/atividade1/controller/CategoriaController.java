
package com.atividade1.controller;

import com.atividade1.model.Categoria;
import com.atividade1.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "categoria/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "categoria/form";
        }
        
        categoriaService.save(categoria);
        attributes.addFlashAttribute("mensagem", "Categoria salva com sucesso!");
        return "redirect:/categorias";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria inválida: " + id)));
        return "categoria/form";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            categoriaService.deleteById(id);
            attributes.addFlashAttribute("mensagem", "Categoria excluída com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagem", "Erro ao excluir categoria!");
        }
        return "redirect:/categorias";
    }
}