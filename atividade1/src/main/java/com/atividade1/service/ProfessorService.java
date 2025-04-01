package com.atividade1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.ProfessorModel;
import com.atividade1.repository.ProfessorRepository;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public List<ProfessorModel> findAll() {
        return professorRepository.findAll();
    }

    public Optional<ProfessorModel> findById(int id) {
        return professorRepository.findById(id);
    }

    public ProfessorModel save(ProfessorModel professor) {
        return professorRepository.save(professor);
    }

    public void deleteById(int id) {
        professorRepository.deleteById(id);
    }

    public void update(ProfessorModel professor) {
        professorRepository.save(professor);
    }
}
