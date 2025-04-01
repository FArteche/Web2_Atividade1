package com.atividade1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.CursoModel;
import com.atividade1.repository.CursoRepository;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoModel> findAll() {
        return cursoRepository.findAll();
    }

    public Optional<CursoModel> findById(int id){
        return cursoRepository.findById(id);
    }

    public CursoModel save(CursoModel curso){
        return cursoRepository.save(curso);
    }

    public void deleteById(int id){
        cursoRepository.deleteById(id);
    }

    public void update(CursoModel curso){
        cursoRepository.save(curso);
    }
}
