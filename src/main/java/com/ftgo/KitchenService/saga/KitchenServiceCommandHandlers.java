package com.ftgo.KitchenService.saga;

import com.ftgo.KitchenService.KitchenService;
import com.ftgo.KitchenService.domain.ticket.TicketDetails;
import com.ftgo.KitchenService.domain.ticket.entity.Ticket;
import com.ftgo.KitchenService.exception.RestaurantDetailsVerificationException;
import com.ftgo.KitchenService.saga.channel.KitchenServiceChannels;
import com.ftgo.KitchenService.saga.command.CancelCreateTicket;
import com.ftgo.KitchenService.saga.command.ConfirmCreateTicket;
import com.ftgo.KitchenService.saga.command.CreateTicket;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static io.eventuate.tram.sagas.participant.SagaReplyMessageBuilder.withLock;

public class KitchenServiceCommandHandlers {
    private KitchenService kitchenService;

    public KitchenServiceCommandHandlers(@Autowired KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel(KitchenServiceChannels.COMMAND_CHANNEL)
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
            return withLock(Ticket.class, ticket.getId()).withSuccess(reply);
        } catch (RestaurantDetailsVerificationException e) {
            return withFailure();
        }
    }

    private Message confirmCreateTicket
            (CommandMessage<ConfirmCreateTicket> cm) {
        Long ticketId = cm.getCommand().getTicketId();
        kitchenService.confirmCreateTicket(ticketId);
        return withSuccess();
    }

    private Message cancelCreateTicket
            (CommandMessage<CancelCreateTicket> cm) {
        Long ticketId = cm.getCommand().getTicketId();
        kitchenService.cancelCreateTicket(ticketId);
        return withSuccess();
    }
}
