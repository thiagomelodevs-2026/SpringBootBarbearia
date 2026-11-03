package br.edu.faculdade.vagas.dto;

import java.util.List;

public record VagaEntrada(
        String titulo,
        String descricao,
        String area,
        String senioridade,
        String local,
        Boolean aceitaIniciante,
        String empresaSlug,
        List<String> tecnologias
) {
}
