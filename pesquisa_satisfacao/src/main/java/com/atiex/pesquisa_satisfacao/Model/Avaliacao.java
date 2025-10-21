package com.atiex.pesquisa_satisfacao.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @PositiveOrZero
    @Column(name = "muito_insatisfeito")
    private Integer muitoInsatisfeito;

    @PositiveOrZero
    @Column(name = "insatisfeito")
    private Integer insatisfeito;

    @PositiveOrZero
    @Column(name = "neutro")
    private Integer neutro;

    @PositiveOrZero
    @Column(name = "satisfeito")
    private Integer satisfeito;

    @PositiveOrZero
    @Column(name = "muito_satisfeito")
    private Integer muitoSatisfeito;
}
