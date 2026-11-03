package br.edu.faculdade.vagas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vagas")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String senioridade;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private boolean aceitaIniciante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vagas_tecnologias",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "tecnologia_id")
    )
    private List<Tecnologia> tecnologias = new ArrayList<>();

    public Vaga() {
    }

    public void trocarDados(
            String titulo,
            String descricao,
            String area,
            String senioridade,
            String local,
            boolean aceitaIniciante,
            Empresa empresa
    ) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.area = area;
        this.senioridade = senioridade;
        this.local = local;
        this.aceitaIniciante = aceitaIniciante;
        this.empresa = empresa;
    }

    public void trocarTecnologias(List<Tecnologia> tecnologias) {
        this.tecnologias.clear();
        this.tecnologias.addAll(tecnologias);
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getArea() {
        return area;
    }

    public String getSenioridade() {
        return senioridade;
    }

    public String getLocal() {
        return local;
    }

    public boolean isAceitaIniciante() {
        return aceitaIniciante;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public List<Tecnologia> getTecnologias() {
        return tecnologias;
    }
}
