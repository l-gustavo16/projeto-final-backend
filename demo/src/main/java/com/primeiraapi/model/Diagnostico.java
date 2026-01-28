package com.primeiraapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidade que representa um Diagnóstico do sistema.
 * Relaciona um Paciente com um Médico e contém informações sobre o diagnóstico.
 */
@Entity
@Table(name = "diagnosticos")
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @Column(length = 1000)
    private String recomendacoes;

    @Column(name = "data_diagnostico")
    private LocalDateTime dataDiagnostico;

    public Diagnostico() {
        this.dataDiagnostico = LocalDateTime.now();
    }

    public Diagnostico(Paciente paciente, Medico medico, String descricao) {
        this.paciente = paciente;
        this.medico = medico;
        this.descricao = descricao;
        this.dataDiagnostico = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRecomendacoes() {
        return recomendacoes;
    }

    public void setRecomendacoes(String recomendacoes) {
        this.recomendacoes = recomendacoes;
    }

    public LocalDateTime getDataDiagnostico() {
        return dataDiagnostico;
    }

    public void setDataDiagnostico(LocalDateTime dataDiagnostico) {
        this.dataDiagnostico = dataDiagnostico;
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", medico=" + medico +
                ", descricao='" + descricao + '\'' +
                ", dataDiagnostico=" + dataDiagnostico +
                '}';
    }
}
