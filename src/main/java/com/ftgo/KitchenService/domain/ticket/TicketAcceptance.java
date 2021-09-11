package com.ftgo.KitchenService.domain.ticket;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketAcceptance {
    private LocalDateTime readyBy;

    public TicketAcceptance(LocalDateTime readyBy) {
        this.readyBy = readyBy;
    }
}
