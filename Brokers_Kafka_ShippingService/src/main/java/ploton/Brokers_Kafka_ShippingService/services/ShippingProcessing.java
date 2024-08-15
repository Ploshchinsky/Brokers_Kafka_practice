package ploton.Brokers_Kafka_ShippingService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ploton.kafka.OrderDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingProcessing {
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "payed_orders", groupId = "shipping_group")
    public void listen(OrderDto message) {
        log.debug("PaymentService: Message from PaymentService - " + message);
        processingPayment(message);
    }

    private void processingPayment(OrderDto dto) {
        dto.setIsSent(true);
        kafkaProducer.sendMessage("sent_orders", dto);
        log.debug("Order is sending ...  - " + dto);
        log.debug("Sending message next to sent_orders topic");
    }

    private Optional<OrderDto> extractDtoFromJson(String json) {
        try {
            return Optional.of(new ObjectMapper().readValue(json, OrderDto.class));
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
            return Optional.empty();
        }
    }
}
