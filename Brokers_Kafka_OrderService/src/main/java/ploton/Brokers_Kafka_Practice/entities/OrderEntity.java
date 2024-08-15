package ploton.Brokers_Kafka_Practice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String clientName;
    @NotNull
    private String clientAddress;
    @NotNull
    private String products;
    @NotNull
    private Double coast;

    private Boolean isPaid;
    private Boolean isSent;
    private Boolean isDelivered;

    @PrePersist
    private void orderCreation() {
        isPaid = false;
        isSent = false;
        isDelivered = false;
    }
}
