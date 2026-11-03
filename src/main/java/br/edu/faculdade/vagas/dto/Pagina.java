package br.edu.faculdade.vagas.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record Pagina<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalDeElementos,
        int totalDePaginas
) {
    public static <T> Pagina<T> de(Page<T> page) {
        return new Pagina<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
