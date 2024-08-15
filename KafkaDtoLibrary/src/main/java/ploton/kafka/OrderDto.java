package ploton.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String products;
    private Boolean isPaid;
    private Boolean isSent;
    private Boolean isDelivered;
}
