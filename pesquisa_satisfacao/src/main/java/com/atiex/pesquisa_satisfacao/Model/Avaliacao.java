package com.atiex.pesquisa_satisfacao.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.atiex.pesquisa_satisfacao.enums.AvaliacaoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avaliacoes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "horario", nullable = false)
    private LocalDateTime horario = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();

    @Column(name = "nota", nullable = false)
    @Enumerated(EnumType.STRING)
    private AvaliacaoEnum nota;

    @Column(name = "canal_divulgacao", nullable = true)
    private String canalDivulgacao;
}
