package com.ftgo.KitchenService.saga.command;

import io.eventuate.tram.commands.common.Command;
import lombok.Data;

@Data
public class CancelCreateTicket implements Command {
    private Long ticketId;

    public CancelCreateTicket(long ticketId) {
        this.ticketId = ticketId;
    }
}