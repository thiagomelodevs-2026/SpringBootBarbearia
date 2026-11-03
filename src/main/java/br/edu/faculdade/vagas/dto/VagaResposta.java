package br.edu.faculdade.vagas.dto;

import br.edu.faculdade.vagas.entity.Vaga;

import java.util.List;

public record VagaResposta(
        Long id,
        String titulo,
        String descricao,
        String area,
        String senioridade,
        String local,
        boolean aceitaIniciante,
        String empresaSlug,
        List<String> tecnologias
) {
    public static VagaResposta de(Vaga vaga) {
        return new VagaResposta(
                vaga.getId(),
                vaga.getTitulo(),
                vaga.getDescricao(),
                vaga.getArea(),
                vaga.getSenioridade(),
                vaga.getLocal(),
                vaga.isAceitaIniciante(),
                vaga.getEmpresa().getSlug(),
                vaga.getTecnologias().stream().map(tecnologia -> tecnologia.getNome()).toList()
        );
    }
}
