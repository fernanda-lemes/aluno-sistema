package com.exemplo.alunos.service;

import com.exemplo.alunos.model.Aluno;
import com.exemplo.alunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() { return alunoRepository.findAll(); }
    public Page<Aluno> listarPagina(Pageable pageable) { return alunoRepository.findAll(pageable); }
    public Aluno salvar(Aluno aluno) { return alunoRepository.save(aluno); }
    public Optional<Aluno> buscarPorId(Long id) { return alunoRepository.findById(id); }
    public void excluir(Long id) { alunoRepository.deleteById(id); }
    public boolean existePorMatricula(String matricula) { return alunoRepository.existsByMatricula(matricula); }
    public Optional<Aluno> buscarPorMatricula(String matricula) { return alunoRepository.findByMatricula(matricula); }
}
