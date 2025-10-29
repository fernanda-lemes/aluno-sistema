package com.exemplo.alunos.repository;

import com.exemplo.alunos.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByMatricula(String matricula);
    Optional<Aluno> findByMatricula(String matricula);
}
