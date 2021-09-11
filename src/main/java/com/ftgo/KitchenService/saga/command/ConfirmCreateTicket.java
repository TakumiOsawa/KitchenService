package com.ftgo.KitchenService.saga.command;

import io.eventuate.tram.commands.common.Command;
import lombok.Data;

@Data
public class ConfirmCreateTicket  implements Command {
    private Long ticketId;

    public ConfirmCreateTicket(Long ticketId) {
        this.ticketId = ticketId;
    }
}