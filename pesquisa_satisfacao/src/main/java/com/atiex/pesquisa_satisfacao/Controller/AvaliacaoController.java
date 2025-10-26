package com.atiex.pesquisa_satisfacao.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atiex.pesquisa_satisfacao.Model.Avaliacao;
import com.atiex.pesquisa_satisfacao.Repository.AvaliacaoRepository;
import com.atiex.pesquisa_satisfacao.dto.AvaliacaoDTO;
import com.atiex.pesquisa_satisfacao.enums.AvaliacaoEnum;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoSSEController avaliacaoSSEController;

    @PostMapping("/avaliacao")
    public ResponseEntity<Avaliacao> salvarAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = new Avaliacao();
        try {
            avaliacao.setNota(AvaliacaoEnum.valueOf(avaliacaoDTO.avaliacao()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
        avaliacao.setCanalDivulgacao(avaliacaoDTO.canal());

        avaliacaoRepository.save(avaliacao);
        avaliacaoSSEController.notificarClientes();

        return ResponseEntity.ok(avaliacao);
    }

}
