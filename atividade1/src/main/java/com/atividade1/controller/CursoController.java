package com.atividade1.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atividade1.model.CursoModel;
import com.atividade1.service.CategoriaService;
import com.atividade1.service.CursoService;
import com.atividade1.service.ProfessorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("curso", cursoService.findAll());
        return "curso/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("curso", new CursoModel());
        model.addAttribute("categoria", categoriaService.findAll());
        model.addAttribute("professor", professorService.findAll());
        return "curso/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid CursoModel curso, BindingResult result, @RequestParam("file") MultipartFile imagem,
            RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categoria", categoriaService.findAll());
            model.addAttribute("professor", professorService.findAll());
            return "curso/form";
        }

        if (!imagem.isEmpty()) {
            try {
                // Define o diretório onde as imagens serão salvas
                String diretorioImagens = "src/main/resources/static/images/";
                byte[] bytes = imagem.getBytes();
                Path path = Paths.get(diretorioImagens + imagem.getOriginalFilename());
                Files.write(path, bytes);

                curso.setImgPath(imagem.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        cursoService.save(curso);
        attributes.addFlashAttribute("mensagem", "Curso salvo com sucesso!");
        return "redirect:/cursos";
    }
}
