package com.ftgo.KitchenService.saga.command;

import com.ftgo.KitchenService.domain.ticket.TicketDetails;
import io.eventuate.tram.commands.CommandDestination;
import io.eventuate.tram.commands.common.Command;
import lombok.Data;

@Data
@CommandDestination("restaurantService")
public class CreateTicket implements Command {
    private Long orderId;
    private TicketDetails ticketDetails;
    private long restaurantId;

    public CreateTicket(long restaurantId, long orderId, TicketDetails ticketDetails) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.ticketDetails = ticketDetails;
    }
}