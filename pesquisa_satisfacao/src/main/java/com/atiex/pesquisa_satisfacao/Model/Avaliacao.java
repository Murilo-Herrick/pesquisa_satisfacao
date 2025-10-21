package com.atiex.pesquisa_satisfacao.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Avaliacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime horario = LocalDateTime.now();

    @PositiveOrZero
    private Integer muitoInsatisfeito;

    @PositiveOrZero
    private Integer insatisfeito;

    @PositiveOrZero
    private Integer neutro;

    @PositiveOrZero
    private Integer satisfeito;

    @PositiveOrZero
    private Integer muitoSatisfeito;
}
