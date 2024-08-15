package ploton.Brokers_Kafka_Practice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ploton.Brokers_Kafka_Practice.entities.OrderEntity;
import ploton.Brokers_Kafka_Practice.models.PaymentDto;
import ploton.Brokers_Kafka_Practice.services.OrderService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<?> newOrder(@Validated @RequestBody OrderEntity entity) {
        return new ResponseEntity<>(orderService.save(entity), HttpStatus.CREATED);
    }

    @PostMapping("/pay/")
    public ResponseEntity<?> payById(
            @RequestBody PaymentDto paymentInfo) {
        return new ResponseEntity<>(orderService.payById(paymentInfo.getOrderId(), paymentInfo), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{clientName}")
    public ResponseEntity<?> findByClientName(@PathVariable("clientName") String clientName) {
        return new ResponseEntity<>(orderService.findByClientName(clientName), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatedById(@PathVariable("id") Long id,
                                         @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(orderService.updatedById(id, updates), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
