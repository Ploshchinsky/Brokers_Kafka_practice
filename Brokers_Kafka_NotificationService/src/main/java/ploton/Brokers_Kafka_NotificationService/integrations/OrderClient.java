package ploton.Brokers_Kafka_NotificationService.integrations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ploton.kafka.OrderDto;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderClient {
    private final RestTemplate restTemplate;
    private String clientAddress = "http://localhost:8080/api/v1/orders/";

    public void sendOrderUpdates(OrderDto orderDto) {
        String url = clientAddress + orderDto.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> updates = new HashMap<>();
        updates.put("isPaid", orderDto.getIsPaid());
        updates.put("isSent", orderDto.getIsSent());
        updates.put("isDelivered", orderDto.getIsDelivered());

        HttpEntity<Map> entity = new HttpEntity<>(updates, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.debug("OrderClient: Update OrderEntity - Success!");
            log.debug("OrderClient:" + response.getBody());
        } else {
            log.warn("OrderClient: Update OrderEntity - Failed!");
        }
    }

    private String extractJsonFromDto(OrderDto orderDto) {
        try {
            return new ObjectMapper().writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
            return null;
        }
    }
}
