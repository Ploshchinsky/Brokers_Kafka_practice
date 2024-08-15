package ploton.Brokers_Kafka_PaymentService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ploton.kafka.OrderDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProcessing {
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "new_orders", groupId = "payment_group")
    public void listen(OrderDto message) {
        log.debug("PaymentService: Message from OrderService - " + message);
        processingPayment(message);
    }

    private void processingPayment(OrderDto dto) {
        if (dto.getIsPaid()) {
            log.debug("PaymentService: Order is paid! Sending next to payed_orders topic...");
            kafkaProducer.sendMessage("payed_orders", dto);
        }
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
