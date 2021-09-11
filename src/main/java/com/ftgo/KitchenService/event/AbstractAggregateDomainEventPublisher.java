package com.ftgo.KitchenService.event;

import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

import java.util.List;
import java.util.function.Function;

/**
 * Event Publisher specialized for a specific aggregate.
 *
 * @param <A> Type of aggregate.
 */

public class AbstractAggregateDomainEventPublisher<A> {
    private Function<A, Object> idSupplier;
    private DomainEventPublisher eventPublisher;
    private Class<A> aggregateType;

    protected AbstractAggregateDomainEventPublisher(DomainEventPublisher eventPublisher,
                                                    Class<A> aggregateType,
                                                    Function<A, Object> idSupplier) {
        this.eventPublisher = eventPublisher;
        this.aggregateType = aggregateType;
        this.idSupplier = idSupplier;
    }

    public void publish(A aggregate, List<DomainEvent> events) {
        eventPublisher.publish(aggregateType, idSupplier.apply(aggregate), events);
    }
}
