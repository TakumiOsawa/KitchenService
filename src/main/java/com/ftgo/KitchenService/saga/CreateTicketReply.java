package com.ftgo.KitchenService.saga;

import lombok.Data;

@Data
public class CreateTicketReply {
    private long ticketId;

    public CreateTicketReply(long ticketId) {
        this.ticketId = ticketId;
    }
}
