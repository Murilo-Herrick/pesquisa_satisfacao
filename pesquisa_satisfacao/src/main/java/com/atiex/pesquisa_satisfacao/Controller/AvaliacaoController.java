package com.atiex.pesquisa_satisfacao.Controller;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atiex.pesquisa_satisfacao.Model.Avaliacao;
import com.atiex.pesquisa_satisfacao.Repository.AvaliacaoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class AvaliacaoController {
    
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoSSEController avaliacaoSSEController;

     @PostMapping("/avaliacao")
    public ResponseEntity<?> incrementarColuna(@RequestBody Map<String, String> body) {
        String coluna = body.get("coluna");
        Optional<Avaliacao> optionalAvaliacao = avaliacaoRepository.findById(1);

        if (!optionalAvaliacao.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Avaliacao avaliacao = optionalAvaliacao.get();

        try {
            Field campo = Avaliacao.class.getDeclaredField(coluna);
            campo.setAccessible(true);

            Integer valorAtual = (Integer) campo.get(avaliacao);
            if (valorAtual == null) valorAtual = 0;

            campo.set(avaliacao, valorAtual + 1);

            avaliacaoRepository.save(avaliacao);

            avaliacaoSSEController.notificarClientes();

            return ResponseEntity.ok(avaliacao);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            return ResponseEntity.badRequest()
                .body("Coluna inválida: " + coluna);
        }
    }
    
}
