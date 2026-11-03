package br.edu.faculdade.vagas;

import com.example.demo.DemoApplication;
import br.edu.faculdade.vagas.dto.VagaEntrada;
import br.edu.faculdade.vagas.entity.Vaga;
import br.edu.faculdade.vagas.repository.VagaRepository;
import br.edu.faculdade.vagas.service.VagaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class, properties = {
        "spring.datasource.url=jdbc:h2:mem:vagas-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class VagaPaginacaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VagaService vagaService;

    @Autowired
    private VagaRepository vagaRepository;

    @BeforeEach
    void prepararVagas() {
        vagaRepository.deleteAll();
        for (int i = 0; i < 12; i++) {
            vagaService.cadastrar(new VagaEntrada(
                    "Vaga " + String.format("%02d", i),
                    "Descrição " + i,
                    "Tecnologia",
                    "Júnior",
                    "Remoto",
                    true,
                    "empresa-exemplo",
                    List.of("Java", "Spring")
            ));
        }
    }

    @Test
    void listaCincoVagasPorPadrao() throws Exception {
        mockMvc.perform(get("/vagas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conteudo", hasSize(5)))
                .andExpect(jsonPath("$.pagina").value(0))
                .andExpect(jsonPath("$.tamanho").value(5))
                .andExpect(jsonPath("$.totalDeElementos").value(12))
                .andExpect(jsonPath("$.totalDePaginas").value(3));
    }

    @Test
    void listaPaginaSolicitadaOrdenadaPorTitulo() throws Exception {
        mockMvc.perform(get("/vagas")
                        .param("page", "1")
                        .param("size", "3")
                        .param("sort", "titulo,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conteudo", hasSize(3)))
                .andExpect(jsonPath("$.conteudo[0].titulo").value("Vaga 03"))
                .andExpect(jsonPath("$.pagina").value(1))
                .andExpect(jsonPath("$.tamanho").value(3));
    }

    @Test
    void rejeitaOrdenacaoPorCampoInexistente() throws Exception {
        mockMvc.perform(get("/vagas").param("sort", "salario"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Não dá para ordenar por 'salario'."));
    }

    @Test
    void limitaTamanhoMaximoDaPaginaA20() throws Exception {
        mockMvc.perform(get("/vagas").param("size", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conteudo", hasSize(12)))
                .andExpect(jsonPath("$.tamanho").value(20));
    }

    @Test
    void atualizaVagaSemSalvarExplicitamente() throws Exception {
        Vaga vaga = vagaRepository.findAll().getFirst();
        String corpo = """
                {
                  "titulo": "Título atualizado",
                  "descricao": "Descrição atualizada",
                  "area": "Tecnologia",
                  "senioridade": "Pleno",
                  "local": "Híbrido",
                  "aceitaIniciante": false,
                  "empresaSlug": "empresa-exemplo",
                  "tecnologias": ["Java"]
                }
                """;

        mockMvc.perform(put("/vagas/{id}", vaga.getId())
                        .contentType("application/json")
                        .content(corpo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Título atualizado"))
                .andExpect(jsonPath("$.senioridade").value("Pleno"));

        mockMvc.perform(get("/vagas").param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conteudo[0].titulo").value("Título atualizado"));
    }
}
