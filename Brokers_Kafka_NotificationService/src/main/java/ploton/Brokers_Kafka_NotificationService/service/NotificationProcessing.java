package ploton.Brokers_Kafka_NotificationService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ploton.Brokers_Kafka_NotificationService.integrations.OrderClient;
import ploton.kafka.OrderDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProcessing {
    private final OrderClient orderClient;

    @KafkaListener(topics = "sent_orders", groupId = "notification_group")
    public void listen(OrderDto message) {
        log.debug("NotificationService: Message from ShippingService: " + message);
        notificationProcessing(message);
    }

    private void notificationProcessing(OrderDto dto) {
        if (dto.getIsSent()) {
            dto.setIsDelivered(true);
            log.debug("NotificationService: Order is delivered! - " + dto);
            orderClient.sendOrderUpdates(dto);
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
