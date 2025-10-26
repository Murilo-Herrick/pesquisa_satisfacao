package com.atiex.pesquisa_satisfacao.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.atiex.pesquisa_satisfacao.Repository.AvaliacaoRepository;
import com.atiex.pesquisa_satisfacao.enums.AvaliacaoEnum;

@RestController
@CrossOrigin(origins = "*")
public class AvaliacaoSSEController {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // Cache em memória para notas e canais
    private final Map<String, Long> cacheNotas = new ConcurrentHashMap<>();
    private final Map<String, Long> cacheCanais = new ConcurrentHashMap<>();

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoSSEController(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        // Inicializa cache com zeros
        for (AvaliacaoEnum nota : AvaliacaoEnum.values()) {
            cacheNotas.put(nota.name(), 0L);
        }
    }

    @GetMapping("/api/stream-notas")
    public SseEmitter streamNotas() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        // Remove emitter quando a conexão fechar
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        // Envia estado inicial
        try {
            emitter.send(Map.of("notas", cacheNotas, "canais", cacheCanais), MediaType.APPLICATION_JSON);
        } catch (IOException e) {
            emitters.remove(emitter);
        }

        return emitter;
    }

    @GetMapping("/api/stream-canais")
    public SseEmitter streamCanais() {
        return streamNotas(); // ambos usam o mesmo SSE, só para simplificar
    }

    public void notificarClientes() {
        Map<String, Long> notasDB = new ConcurrentHashMap<>();
        for (AvaliacaoEnum nota : AvaliacaoEnum.values()) {
            notasDB.put(nota.name(), avaliacaoRepository.countByNota(nota));
        }
        cacheNotas.putAll(notasDB);

        // Canais
        Map<String, Long> canaisDB = new ConcurrentHashMap<>();
        List<Object[]> canais = avaliacaoRepository.countByCanalGroup();
        for (Object[] row : canais) {
            String canal = (String) row[0];
            Long count = (Long) row[1];
            canaisDB.put(canal, count);
        }
        cacheCanais.putAll(canaisDB);

        // Envia para todos os emitters
        emitters.forEach(emitter -> {
            try {
                emitter.send(Map.of("notas", cacheNotas, "canais", cacheCanais), MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        });
    }

}
