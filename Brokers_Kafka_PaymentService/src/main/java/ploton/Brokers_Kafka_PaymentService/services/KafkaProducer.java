package ploton.Brokers_Kafka_PaymentService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ploton.kafka.OrderDto;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public void sendMessage(String topic, OrderDto message) {
        kafkaTemplate.send(topic, message);
    }
}
