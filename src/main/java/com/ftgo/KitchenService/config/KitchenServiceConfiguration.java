package com.ftgo.KitchenService.config;

import com.ftgo.KitchenService.KitchenService;
import com.ftgo.KitchenService.domain.ticket.repository.TicketRepository;
import com.ftgo.KitchenService.event.TicketDomainEventPublisher;
import com.ftgo.KitchenService.saga.KitchenServiceCommandHandlers;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.common.NoopDuplicateMessageDetector;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TramEventsPublisherConfiguration.class,
        TramMessageProducerJdbcConfiguration.class,
        EventuateTramKafkaMessageConsumerConfiguration.class,
        SagaParticipantConfiguration.class })
public class KitchenServiceConfiguration {
    @Bean
    public KitchenService kitchenService(TicketRepository ticketRepository,
                                         TicketDomainEventPublisher eventPublisher) {
        return new KitchenService(ticketRepository, eventPublisher);
    }

    @Bean
    public TicketDomainEventPublisher eventPublisher(DomainEventPublisher eventPublisher) {
        return new TicketDomainEventPublisher(eventPublisher);
    }

    @Bean
    public DuplicateMessageDetector duplicateMessageDetector() { return new NoopDuplicateMessageDetector(); }

    @Bean
    public KitchenServiceCommandHandlers kitchenCommandHandlers(KitchenService kitchenService) {
        return new KitchenServiceCommandHandlers(kitchenService);
    }

    @Bean
    public SagaCommandDispatcher kitchenCommandHandlersDispatcher(KitchenServiceCommandHandlers kitchenCommandHandlers,
                                                                   SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
        return sagaCommandDispatcherFactory.make("kitchenService",
                kitchenCommandHandlers.commandHandlers());
    }
}
