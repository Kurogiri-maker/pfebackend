package com.example.TalanCDZ.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableKafka
public class KafkaConfiguration {


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Replace with your Kafka bootstrap servers
        return new KafkaAdmin(configs);
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfigurationProperties());
    }

    @Bean
    public NewTopic enrichment() {
        return new NewTopic("enrichissement-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic newAttributesTopic() {
        return new NewTopic("new-attributes-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic enrichmentTopic() {
        return new NewTopic("enrichissement", 1, (short) 1);
    }

}