package com.ftgo.KitchenService.ticket;

import com.ftgo.KitchenService.event.TicketDomainEvent;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import lombok.Getter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Ticket Aggregate
 */

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @Getter
    private Long id;
    private TicketState state;
    private Long restaurantId;

    @ElementCollection
    @CollectionTable(name = "ticket_line_items")
    private List<TicketLineItem> lineItems;

    private ZonedDateTime readyBy;
    private ZonedDateTime acceptTime;
    private ZonedDateTime preparingTime;
    private ZonedDateTime pickedUpTime;
    private ZonedDateTime readyForPickupTime;

    public static ResultWithEvents<Ticket, TicketDomainEvent> create(Long id, TicketDetails details) {
        return new ResultWithEvents<>(new Ticket(id, details), new TicketCreated(id, details));
    }

    public List<DomainEvent> accept(ZonedDateTime readyBy) {
        acceptTime = ZonedDateTime.now();
        this.readyBy = readyBy;
        return Collections.singletonList(new TicketAcceptedEvent(readyBy));
    }

    public List<TicketPreparationStarted> preparing() {
        switch (state) {
            case ACCEPTED:
                state = TicketState.PREPARING;
                preparingTime = ZonedDateTime.now();
                return Collections.singletonList(new TicketPreparationStarted());
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }

    public List<TicketDomainEvent> cancel() {
        switch (state) {
            case CREATED:
            case ACCEPTED:
                state = TicketState.CANCELLED;
                return Collections.singletonList(new TicketCancelled());
            case READY_FOR_PICKUP:
                throw new TicketCannotBeCancelledException();
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
}
