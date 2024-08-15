package ploton.Brokers_Kafka_Practice.services;

import ploton.Brokers_Kafka_Practice.entities.OrderEntity;
import ploton.Brokers_Kafka_Practice.models.PaymentDto;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderEntity save(OrderEntity entity);

    OrderEntity findById(Long id);

    List<OrderEntity> findByClientName(String clientName);

    List<OrderEntity> findAll();

    OrderEntity updatedById(Long id, Map<String, Object> updates);

    Long deleteById(Long id);

    OrderEntity payById(Long id, PaymentDto paymentInfo);
}
