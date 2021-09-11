package com.ftgo.KitchenService.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketAccepted implements TicketDomainEvent{
    private LocalDateTime readyBy;

    public TicketAccepted(LocalDateTime readyBy) {
        this.readyBy = readyBy;
    }
}
