package br.edu.faculdade.vagas.repository;

import br.edu.faculdade.vagas.entity.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

    @Override
    @EntityGraph(attributePaths = {"empresa", "tecnologias"})
    Page<Vaga> findAll(Pageable pageable);
}
