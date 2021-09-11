package com.ftgo.KitchenService.domain.ticket.repository;

import com.ftgo.KitchenService.domain.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of ticket aggregate.
 */

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
