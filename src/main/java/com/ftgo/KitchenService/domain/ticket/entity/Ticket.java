package com.ftgo.KitchenService.domain.ticket.entity;

import com.ftgo.KitchenService.domain.ticket.TicketDetails;
import com.ftgo.KitchenService.domain.ticket.TicketLineItem;
import com.ftgo.KitchenService.domain.ticket.TicketState;
import com.ftgo.KitchenService.event.TicketAccepted;
import com.ftgo.KitchenService.event.TicketCreated;
import com.ftgo.KitchenService.event.TicketDomainEvent;
import com.ftgo.KitchenService.exception.UnsupportedStateTransitionException;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import lombok.Getter;
import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ticket Aggregate
 */

@Entity
@Table(name = "tickets")
@Access(AccessType.FIELD)
public class Ticket {
    @Id
    @Getter
    @Column(name = "ticket_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private TicketState state;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @ElementCollection
    @CollectionTable(name = "ticket_line_items")
    private List<TicketLineItemOnDB> lineItems;

    @Column(name = "ready_by")
    private ZonedDateTime readyBy;

    @Column(name = "accept_time")
    private ZonedDateTime acceptTime;

    public Ticket() {}

    public Ticket(long restaurantId, Long id, TicketDetails details) {
        this.restaurantId = restaurantId;
        this.id = id;
        this.state = TicketState.CREATE_PENDING;
        this.lineItems = details.getLineItems().stream()
                .map(TicketLineItem::transformEmbeddable)
                .collect(Collectors.toList());
    }

    public static ResultWithEvents<Ticket> create(long restaurantId, Long id, TicketDetails details) {
        return new ResultWithEvents<>(new Ticket(restaurantId, id, details),
                new TicketCreated(id, details));
    }

    public List<DomainEvent> confirmCreate() {
        if (state == TicketState.CREATE_PENDING) {
            state = TicketState.AWAITING_ACCEPTANCE;
            return Collections.singletonList(new TicketCreated(id,
                    TicketDetails.create(id, lineItems.stream()
                            .map(TicketLineItemOnDB::transformNotEmbeddable)
                            .collect(Collectors.toList()))));
        }
        else{
            throw new UnsupportedStateTransitionException(state);
        }
    }

    public List<DomainEvent> cancelCreate() {
        throw new NotYetImplementedException();
    }

    public List<DomainEvent> accept(ZonedDateTime readyBy) {
        acceptTime = ZonedDateTime.now();
        this.readyBy = readyBy;
        return Collections.singletonList(new TicketAccepted(
                LocalDateTime.ofInstant(readyBy.toInstant(), ZoneId.systemDefault())));
    }
}
