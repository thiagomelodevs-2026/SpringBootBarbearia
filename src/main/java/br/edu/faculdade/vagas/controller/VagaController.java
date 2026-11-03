package br.edu.faculdade.vagas.controller;

import br.edu.faculdade.vagas.dto.Pagina;
import br.edu.faculdade.vagas.dto.VagaEntrada;
import br.edu.faculdade.vagas.dto.VagaResposta;
import br.edu.faculdade.vagas.service.VagaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/vagas")
public class VagaController {

    private final VagaService servico;

    public VagaController(VagaService servico) {
        this.servico = servico;
    }

    @GetMapping
    public Pagina<VagaResposta> listar(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return servico.listar(pageable);
    }

    @PostMapping
    public ResponseEntity<VagaResposta> cadastrar(@RequestBody VagaEntrada nova) {
        if (!dadosValidos(nova)) {
            return ResponseEntity.badRequest().build();
        }

        VagaResposta criada = servico.cadastrar(nova);
        return ResponseEntity.created(URI.create("/vagas/" + criada.id())).body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VagaResposta> trocar(@PathVariable Long id, @RequestBody VagaEntrada nova) {
        if (!dadosValidos(nova)) {
            return ResponseEntity.badRequest().build();
        }

        return servico.trocar(id, nova)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private boolean dadosValidos(VagaEntrada nova) {
        return nova != null
                && naoVazio(nova.titulo())
                && naoVazio(nova.descricao())
                && naoVazio(nova.area())
                && naoVazio(nova.senioridade())
                && naoVazio(nova.local())
                && naoVazio(nova.empresaSlug())
                && nova.tecnologias() != null
                && nova.tecnologias().stream().allMatch(this::naoVazio);
    }

    private boolean naoVazio(String valor) {
        return valor != null && !valor.isBlank();
    }
}
