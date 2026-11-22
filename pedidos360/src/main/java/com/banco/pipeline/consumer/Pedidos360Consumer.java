package com.banco.pipeline.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Pedidos360Consumer {

    @KafkaListener(topics = "pedidos360-alerta", groupId = "pedidos360-alertas-group")
    public void consumirAlertaStock(String mensajeJson) {
        System.out.println("[CONSUMIDOR KAFKA - ALERTAS] Recibido de 'pedidos360-alerta': " + mensajeJson);
    }

    @KafkaListener(topics = "pedidos360-pedidos", groupId = "pedidos360-pedidos-group")
    public void consumirPedido(String mensajeJson) {
        System.out.println("[CONSUMIDOR KAFKA - PEDIDOS] Recibido de 'pedidos360-pedidos': " + mensajeJson);
    }

    @KafkaListener(topics = "banco-alertas", groupId = "pedidos360-banco-alertas-group")
    public void consumirAlertaFraude(String mensajeJson) {
        System.out.println("[CONSUMIDOR KAFKA - FRAUDE] Recibido de 'banco-alertas': " + mensajeJson);
    }
}
