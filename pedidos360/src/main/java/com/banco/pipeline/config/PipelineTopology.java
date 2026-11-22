package com.banco.pipeline.config;

import com.banco.pipeline.model.AlertaFraude;
import com.banco.pipeline.model.AlertaStock;
import com.banco.pipeline.model.Pedido;
import com.banco.pipeline.model.Transaccion;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;

@Configuration
public class PipelineTopology {

    @Bean
    public KStream<String, Transaccion> kStreamPipeline(StreamsBuilder builder) {
        JacksonJsonSerde<Transaccion> txSerde = new JacksonJsonSerde<>(Transaccion.class);
        JacksonJsonSerde<AlertaFraude> alertSerde = new JacksonJsonSerde<>(AlertaFraude.class);

        KStream<String, Transaccion> entrada = builder.stream(
                "banco-transacciones", Consumed.with(Serdes.String(), txSerde));

        KStream<String, Transaccion> validas = entrada
                .filter((key, tx) -> tx != null && tx.getMonto() != null && tx.getMonto() > 10)
                .peek((key, tx) -> System.out.println("Procesada: " + tx.getMonto()));

        KStream<String, AlertaFraude> alertas = validas
                .filter((key, tx) -> tx.getMonto() > 10000)
                .mapValues(tx -> new AlertaFraude(
                        tx.getUsuario(),
                        tx.getMonto(),
                        "Transaccion inusualmente alta en " + tx.getOrigen(),
                        "ALTA"));

        alertas.peek((key, a) -> System.out.println("ALERTA: " + a.getMotivo()))
                .to("banco-alertas", Produced.with(Serdes.String(), alertSerde));

        return entrada;
    }

    @Bean
    public KStream<String, Pedido> pedidos360Pipeline(StreamsBuilder builder) {
        JacksonJsonSerde<Pedido> pedidoSerde = new JacksonJsonSerde<>(Pedido.class);
        JacksonJsonSerde<AlertaStock> alertaStockSerde = new JacksonJsonSerde<>(AlertaStock.class);

        KStream<String, Pedido> entrada = builder.stream(
                "pedidos360-pedidos", Consumed.with(Serdes.String(), pedidoSerde));

        KStream<String, Pedido> validos = entrada
                .filter((key, p) -> p != null && p.getCantidad() != null && p.getCantidad() > 0)
                .peek((key, p) -> System.out.println("Pedidos360 - Venta procesada: " + p.getProducto() + " (" + p.getCantidad() + " uds)"));

        KStream<String, AlertaStock> alertas = validos
                .filter((key, p) -> p.getCantidad() >= 4)
                .mapValues(p -> new AlertaStock(
                        p.getProducto(),
                        p.getCantidad(),
                        p.getOrigen(),
                        "Venta mayor o igual a 4 unidades en " + p.getOrigen() + " - revisar existencias",
                        "ALTA"));

        alertas.peek((key, a) -> System.out.println("Pedidos360 ALERTA: " + a.getProducto() + " -> " + a.getMotivo()))
                .to("pedidos360-alerta", Produced.with(Serdes.String(), alertaStockSerde));

        return entrada;
    }
}
