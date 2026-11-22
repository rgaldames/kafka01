package com.banco.pipeline.controller;

import com.banco.pipeline.model.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pedidos360")
public class Pedidos360Controller {

    private final KafkaTemplate<String, Pedido> kafka;

    public Pedidos360Controller(KafkaTemplate<String, Pedido> kafka) {
        this.kafka = kafka;
    }

    @PostMapping("/pedido")
    public ResponseEntity<String> enviarPedido(@RequestBody Pedido pedido) {
        if (pedido == null
                || pedido.getProducto() == null || pedido.getProducto().isBlank()
                || pedido.getCantidad() == null || pedido.getCantidad() <= 0
                || pedido.getOrigen() == null || pedido.getOrigen().isBlank()) {
            return ResponseEntity.badRequest().body("Datos invalidos: indique producto, cantidad (> 0) y origen");
        }
        if (pedido.getTimestamp() == null) {
            pedido.setTimestamp(System.currentTimeMillis());
        }
        kafka.send("pedidos360-pedidos", pedido.getProducto(), pedido);
        return ResponseEntity.accepted().body("Pedido recibido para envio a Pedidos360");
    }

    @GetMapping("/reglas")
    public Map<String, Object> obtenerReglas() {
        return Map.of(
                "servicio", "Pedidos360 Retail",
                "topicEntrada", "pedidos360-pedidos",
                "topicAlertas", "pedidos360-alerta",
                "umbralAlertaUnidades", 4,
                "descripcion",
                "Ventas de 1 a 3 unidades se procesan normalmente; mayores o iguales a 4 generan alerta de existencias.");
    }

    @GetMapping("/estado")
    public Map<String, String> estado() {
        return Map.of(
                "aplicacion", "Pedidos360",
                "estado", "ACTIVO");
    }
}
