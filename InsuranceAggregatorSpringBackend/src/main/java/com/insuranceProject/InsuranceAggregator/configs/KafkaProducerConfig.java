/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.insuranceProject.InsuranceAggregator.configs;

import org.springframework.context.annotation.Configuration;

/**
 *
 * @author pcran
 */
@Configuration
public class KafkaProducerConfig {

//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstapServers;
//
//    public ProducerFactory<String, List<User>> producerConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstapServers);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//
//    @Bean
//    public KafkaTemplate<String, List<User>> kafkaTemplate() {
//        return new KafkaTemplate<>(producerConfig());
//    }
}
