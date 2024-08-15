package ploton.Brokers_Kafka_Practice.cfg;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic newOrdersTopic() {
        return new NewTopic("new_orders", 1, (short) 1);
    }

    @Bean
    public NewTopic payedOrdersTopic() {
        return new NewTopic("payed_orders", 1, (short) 1);
    }

    @Bean
    public NewTopic sentOrdersTopic() {
        return new NewTopic("sent_orders", 1, (short) 1);
    }

    @Bean
    public NewTopic dlqTopic() {
        return new NewTopic("dlq", 1, (short) 1);
    }
}
