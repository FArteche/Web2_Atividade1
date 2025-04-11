package com.atividade1.controller;

import com.atividade1.model.Curso;
import com.atividade1.service.CategoriaService;
import com.atividade1.service.CursoService;
import com.atividade1.service.ProfessorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "curso/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("professores", professorService.findAll());
        return "curso/form";
    }
    
    @PostMapping("/salvar")
    public String salvar(@Valid Curso curso, BindingResult result, 
                         @RequestParam("file") MultipartFile imagem, 
                         RedirectAttributes attributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.findAll());
            model.addAttribute("professores", professorService.findAll());
            return "curso/form";
        }
        
        // Tratamento da imagem (simplificado)
        if (!imagem.isEmpty()) {
            try {
                // Define o diretório onde as imagens serão salvas
                String diretorioImagens = "src/main/resources/static/images/";
                byte[] bytes = imagem.getBytes();
                Path path = Paths.get(diretorioImagens + imagem.getOriginalFilename());
                Files.write(path, bytes);
                
                curso.setImagem(imagem.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        cursoService.save(curso);
        attributes.addFlashAttribute("mensagem", "Curso salvo com sucesso!");
        return "redirect:/cursos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("curso", cursoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso inválido: " + id)));
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("professores", professorService.findAll());
        return "curso/form";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            cursoService.deleteById(id);
            attributes.addFlashAttribute("mensagem", "Curso excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagem", "Erro ao excluir curso!");
        }
        return "redirect:/cursos";
    }
}