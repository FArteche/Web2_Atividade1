package com.atividade1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.CategoriaModel;
import com.atividade1.repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaModel> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<CategoriaModel>findById(int id) {
        return categoriaRepository.findById(id);
    }

    public CategoriaModel save(CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteById(int id) {
        categoriaRepository.deleteById(id);
    }

    public void update(CategoriaModel categoria) {
        categoriaRepository.save(categoria);
    }
}
