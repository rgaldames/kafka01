package com.banco.pipeline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;
@SpringBootApplication
@EnableKafkaStreams
public class BancoPipelineApplication {
    public static void main(String[] args) {
        SpringApplication.run(BancoPipelineApplication.class, args);
    }
}
