package com.ftgo.KitchenService;

import com.ftgo.KitchenService.ticket.Ticket;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandHandlersBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import org.springframework.beans.factory.annotation.Autowired;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

/**
 * Command Handler of KitchenService.
 */

public class KitchenServiceCommandHandler {
    private final KitchenService kitchenService;

    public KitchenServiceCommandHandler(@Autowired KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    public CommandHandlers commandHandlers() {
        return CommandHandlersBuilder
                .fromChannel("orderService")
                .onMessage(CreateTicket.class, this::createTicket)
                .onMessage(ConfirmCreateTicket.class, this::confirmCreateTicket)
                .onMessage(CancelCreateTicket.class, this::cancelCreateTicket)
                .build();
    }

    private Message createTicket(CommandMessage<CreateTicket> cm) {
        CreateTicket command = cm.getCommand();
        long restaurantId = command.getRestaurantId();
        Long ticketId = command.getOrderId();
        TicketDetails ticketDetails = command.getTicketDetails();

        try {
            Ticket ticket = kitchenService.createTicket(restaurantId, ticketId, ticketDetails);
            CreateTicketReply reply = new CreateTicketReply(ticket.getId());
            return withSuccess(reply);
        }
        catch (RestaurantDetailsVerificationException e) {
            return withFailure();
        }
    }

    private Message confirmCreateTicket(CommandMessage<ConfirmCreateTicket> cm) {
        Long ticketId = cm.getCommand().getTicketId();
        kitchenService.confirmCreateTicket(ticketId);
        return withSuccess();
    }
}
