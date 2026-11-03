package br.edu.faculdade.vagas.repository;

import br.edu.faculdade.vagas.entity.Tecnologia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {

    Optional<Tecnologia> findByNome(String nome);
}
