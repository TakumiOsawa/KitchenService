package com.ftgo.KitchenService.event;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Event Consumer of KitchenService.
 */

public class KitchenServiceEventConsumer {
    private final RestaurantService restaurantService;

    public KitchenServiceEventConsumer(@Autowired RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType("com.ftgo.RestaurantService.Restaurant")
                .onEvent(RestaurantMenuRevised.class, this::reviseMenu)
                .build();
    }

    public void reviseMenu(DomainEventEnvelope<RestrauntMenuRevised> de) {
        long id = Long.parseLong(de.getAggregateId());
        RestaurantMenu revisedMenu = de.getEvent().getRevisedMenu();
        restaurantService.reviseMenu(id, revisedMenu);
    }
}
