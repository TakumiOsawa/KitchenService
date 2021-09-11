package com.ftgo.KitchenService.domain.ticket;

import com.ftgo.KitchenService.domain.ticket.entity.TicketLineItemOnDB;
import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * Line item of ticket.
 */

@Getter
public class TicketLineItem {
    private int quantity;
    private String menuItemId;
    private String name;

    private TicketLineItem(String menuItemId, String name, int quantity) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.quantity = quantity;
    }

    public static TicketLineItem create(String menuItemId, String name, int quantity){
        return new TicketLineItem(menuItemId, name, quantity);
    }

    public TicketLineItemOnDB transformEmbeddable() {
        return new TicketLineItemOnDB(menuItemId, name, quantity);
    }
}