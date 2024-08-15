package ploton.Brokers_Kafka_Practice.models;

import lombok.Data;

@Data
public class PaymentDto {
    private Long orderId;
    private Double paymentValue;
}
