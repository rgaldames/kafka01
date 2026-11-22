package com.banco.pipeline.controller;

import com.banco.pipeline.model.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banco")
public class SimuladorController {

    private final KafkaTemplate<String, Transaccion> kafka;

    public SimuladorController(KafkaTemplate<String, Transaccion> kafka) {
        this.kafka = kafka;
    }

    @PostMapping("/transaccion")
    public ResponseEntity<String> enviar(@RequestBody Transaccion tx) {
        if (tx == null || tx.getUsuario() == null || tx.getUsuario().isBlank()
                || tx.getMonto() == null || tx.getMonto() < 0 || tx.getOrigen() == null) {
            return ResponseEntity.badRequest().body("Datos invalidos");
        }
        if (tx.getTimestamp() == null) {
            tx.setTimestamp(System.currentTimeMillis());
        }
        kafka.send("banco-transacciones", tx.getUsuario(), tx);
        return ResponseEntity.accepted().body("Transaccion recibida para envio");
    }
}
