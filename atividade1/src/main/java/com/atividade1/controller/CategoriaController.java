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

import com.atividade1.model.CategoriaModel;
import com.atividade1.service.CategoriaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categoria", categoriaService.findAll());
        return "categoria/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("categoria", new CategoriaModel());
        return "categoria/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid CategoriaModel categoria, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return "categoria/form";
        }

        categoriaService.save(categoria);
        attributes.addFlashAttribute("Mensagem", "Categoria Salva com Sucesso!");
        return "redirect:/categoria/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable int id, Model model){
        model.addAttribute("categoria", categoriaService.findById(id).orElseThrow(()-> new IllegalArgumentException("Categoria Inválida "+id)));
        return "categoria/form";
    }

    @GetMapping
    public String excluir(@PathVariable int id, RedirectAttributes attributes){
        try{
            categoriaService.deleteById(id);
            attributes.addFlashAttribute("Mensagem", "Categoria Excluida com Sucesso!");
        }catch(Exception e){
            attributes.addFlashAttribute("Mensagem", "Erro ao Excluir Categoria!");
        }
        return "redirect:/categorias";
    }
}
