package com.ftgo.KitchenService;

import com.ftgo.KitchenService.domain.ticket.TicketDetails;
import com.ftgo.KitchenService.event.TicketDomainEvent;
import com.ftgo.KitchenService.event.TicketDomainEventPublisher;
import com.ftgo.KitchenService.exception.TicketNotFoundException;
import com.ftgo.KitchenService.domain.ticket.repository.TicketRepository;
import com.ftgo.KitchenService.domain.ticket.entity.Ticket;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service to create ticket.
 */

public class KitchenService {
    private final TicketRepository ticketRepository;
    private final TicketDomainEventPublisher domainEventPublisher;

    public KitchenService(@Autowired TicketRepository ticketRepository,
                          @Autowired TicketDomainEventPublisher eventPublisher) {
        this.ticketRepository = ticketRepository;
        this.domainEventPublisher = eventPublisher;
    }

    public Ticket createTicket(long restaurantId, Long ticketId, TicketDetails ticketDetails) {
        ResultWithEvents<Ticket> rwe = Ticket.create(restaurantId, ticketId, ticketDetails);
        ticketRepository.save(rwe.result);
        domainEventPublisher.publish(rwe.result, rwe.events);
        return rwe.result;
    }

    public void confirmCreateTicket(Long ticketId) {
        Ticket ro = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        List<DomainEvent> events = ro.confirmCreate();
        domainEventPublisher.publish(ro, events);
    }

    public void cancelCreateTicket(Long ticketId) {
        Ticket ro = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        List<DomainEvent> events = ro.cancelCreate();
        domainEventPublisher.publish(ro, events);
    }

    public void accept(long ticketId, ZonedDateTime readyBy) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        List<DomainEvent> events = ticket.accept(readyBy);
        domainEventPublisher.publish(ticket, events);
    }
}
