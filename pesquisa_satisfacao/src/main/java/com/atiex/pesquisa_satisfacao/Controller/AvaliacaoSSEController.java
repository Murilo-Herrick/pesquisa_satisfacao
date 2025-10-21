package com.atiex.pesquisa_satisfacao.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.atiex.pesquisa_satisfacao.Model.Avaliacao;
import com.atiex.pesquisa_satisfacao.Repository.AvaliacaoRepository;

@RestController
@RequestMapping("/api")
public class AvaliacaoSSEController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/stream-avaliacoes")
    public SseEmitter streamAvaliacoes() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // timeout infinito
        emitters.add(emitter);

        // Remove emitter quando a conexão fechar
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        // Envia o estado atual imediatamente
        try {
            emitter.send(getAvaliacoes(), MediaType.APPLICATION_JSON);
        } catch (IOException e) {
            emitters.remove(emitter);
        }

        return emitter;
    }

    public void notificarClientes() {
        Map<String, Integer> avaliacoes = getAvaliacoes();
        emitters.forEach(emitter -> {
            try {
                emitter.send(avaliacoes, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        });
    }

    private Map<String, Integer> getAvaliacoes() {
        Avaliacao a = avaliacaoRepository.findById(1).orElse(new Avaliacao());
        Map<String, Integer> map = new HashMap<>();
        map.put("Muito Insatisfeito", a.getMuitoInsatisfeito());
        map.put("Insatisfeito", a.getInsatisfeito());
        map.put("Neutro", a.getNeutro());
        map.put("Satisfeito", a.getSatisfeito());
        map.put("Muito Satisfeito", a.getMuitoSatisfeito());
        return map;
    }
}
