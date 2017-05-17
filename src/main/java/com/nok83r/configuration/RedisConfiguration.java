package com.nok83r.configuration;

import com.nok83r.Receiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;


/**
 * The type Redis configuration.
 */
@Configuration
public class RedisConfiguration {

    /**
     * Container redis message listener container.
     *
     * @param connectionFactory the connection factory
     * @param listenerAdapter   the listener adapter
     * @return the redis message listener container
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    /**
     * Listener adapter message listener adapter.
     *
     * @param receiver the receiver
     * @return the message listener adapter
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    /**
     * Receiver receiver.
     *
     * @param latch the latch
     * @return the receiver
     */
    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    /**
     * Latch count down latch.
     *
     * @return the count down latch
     */
    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    /**
     * Template string redis template.
     *
     * @param connectionFactory the connection factory
     * @return the string redis template
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
