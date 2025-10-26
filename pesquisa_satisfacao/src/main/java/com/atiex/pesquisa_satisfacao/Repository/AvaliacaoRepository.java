package com.atiex.pesquisa_satisfacao.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.atiex.pesquisa_satisfacao.Model.Avaliacao;
import com.atiex.pesquisa_satisfacao.enums.AvaliacaoEnum;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    long countByNota(AvaliacaoEnum nota);

    @Query("SELECT a.canalDivulgacao, COUNT(a) FROM Avaliacao a GROUP BY a.canalDivulgacao")
    List<Object[]> countByCanalGroup();
}
