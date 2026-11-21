package com.kafka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kafka")
public class KafkaLabApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaLabApplication.class, args);
	}

}
