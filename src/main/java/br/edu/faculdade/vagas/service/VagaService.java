package br.edu.faculdade.vagas.service;

import br.edu.faculdade.vagas.dto.Pagina;
import br.edu.faculdade.vagas.dto.VagaEntrada;
import br.edu.faculdade.vagas.dto.VagaResposta;
import br.edu.faculdade.vagas.entity.Empresa;
import br.edu.faculdade.vagas.entity.Tecnologia;
import br.edu.faculdade.vagas.entity.Vaga;
import br.edu.faculdade.vagas.repository.EmpresaRepository;
import br.edu.faculdade.vagas.repository.TecnologiaRepository;
import br.edu.faculdade.vagas.repository.VagaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    private final VagaRepository repositorio;
    private final EmpresaRepository empresaRepositorio;
    private final TecnologiaRepository tecnologiaRepositorio;

    public VagaService(
            VagaRepository repositorio,
            EmpresaRepository empresaRepositorio,
            TecnologiaRepository tecnologiaRepositorio
    ) {
        this.repositorio = repositorio;
        this.empresaRepositorio = empresaRepositorio;
        this.tecnologiaRepositorio = tecnologiaRepositorio;
    }

    @Transactional(readOnly = true)
    public Pagina<VagaResposta> listar(Pageable pageable) {
        return Pagina.de(
                repositorio.findAll(pageable)
                        .map(VagaResposta::de)
        );
    }

    @Transactional
    public VagaResposta cadastrar(VagaEntrada nova) {
        Vaga vaga = new Vaga();
        vaga.trocarDados(
                nova.titulo(),
                nova.descricao(),
                nova.area(),
                nova.senioridade(),
                nova.local(),
                Boolean.TRUE.equals(nova.aceitaIniciante()),
                empresaDo(nova.empresaSlug())
        );
        vaga.trocarTecnologias(tecnologiasDe(nova.tecnologias()));
        return VagaResposta.de(repositorio.save(vaga));
    }

    @Transactional
    public Optional<VagaResposta> trocar(Long id, VagaEntrada nova) {
        return repositorio.findById(id).map(existente -> {
            existente.trocarDados(
                    nova.titulo(),
                    nova.descricao(),
                    nova.area(),
                    nova.senioridade(),
                    nova.local(),
                    Boolean.TRUE.equals(nova.aceitaIniciante()),
                    empresaDo(nova.empresaSlug())
            );
            existente.trocarTecnologias(tecnologiasDe(nova.tecnologias()));
            return VagaResposta.de(existente);
        });
    }

    private Empresa empresaDo(String slug) {
        return empresaRepositorio.findBySlug(slug)
                .orElseGet(() -> empresaRepositorio.save(new Empresa(slug)));
    }

    private List<Tecnologia> tecnologiasDe(List<String> nomes) {
        return nomes.stream()
                .map(String::trim)
                .distinct()
                .map(nome -> tecnologiaRepositorio.findByNome(nome)
                        .orElseGet(() -> tecnologiaRepositorio.save(new Tecnologia(nome))))
                .toList();
    }
}
