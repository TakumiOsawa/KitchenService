package com.ftgo.KitchenService.domain.ticket;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public class TicketDetails {
    @Getter
    private List<TicketLineItem> lineItems;

    private TicketDetails(List<TicketLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public static TicketDetails create(Long id, List<TicketLineItem> lineItems) {
        return new TicketDetails(lineItems);
    }
}
