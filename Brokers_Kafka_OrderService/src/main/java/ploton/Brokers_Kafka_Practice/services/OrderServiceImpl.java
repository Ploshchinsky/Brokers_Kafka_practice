package ploton.Brokers_Kafka_Practice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ploton.Brokers_Kafka_Practice.entities.OrderEntity;
import ploton.Brokers_Kafka_Practice.models.PaymentDto;
import ploton.Brokers_Kafka_Practice.repositories.OrderRepository;
import ploton.Brokers_Kafka_Practice.utils.EntityUtils;
import ploton.kafka.OrderDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public OrderEntity save(OrderEntity entity) {
        OrderEntity order = orderRepository.save(entity);
        OrderDto dto = new OrderDto(
                order.getId(),
                order.getProducts(),
                order.getIsPaid(),
                order.getIsSent(),
                order.getIsDelivered()
        );
        kafkaProducer.sendMessage("new_orders", dto);
        log.debug("OderServiceImpl -> save(): Kafka sending message success: " + dto);
        return order;
    }

    @Override
    public OrderEntity payById(Long id, PaymentDto paymentInfo) {
        OrderEntity order = findById(id);
        if (paymentInfo.getPaymentValue() >= order.getCoast()) {
            order.setIsPaid(true);
            updatedById(id, Collections.singletonMap("isPaid", true));

            OrderDto dto = new OrderDto(
                    order.getId(),
                    order.getProducts(),
                    order.getIsPaid(),
                    order.getIsSent(),
                    order.getIsDelivered()
            );

            kafkaProducer.sendMessage("new_orders", dto);
            log.debug("OderServiceImpl -> payById(): Kafka sending message success: " + dto);
        }
        return order;
    }

    @Override
    public OrderEntity findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Order ID - " + id));
    }

    @Override
    public List<OrderEntity> findByClientName(String clientName) {
        return orderRepository.findByClientName(clientName)
                .orElseThrow(() -> new NoSuchElementException("Order [Client Name] - " + clientName));
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity updatedById(Long id, Map<String, Object> updates) {
        OrderEntity order = findById(id);
        EntityUtils.updatedEntity(order, updates);
        return orderRepository.save(order);
    }

    @Override
    public Long deleteById(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return id;
        }
        return -1l;
    }
}
