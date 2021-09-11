package com.ftgo.KitchenService.domain.ticket.entity;

import com.ftgo.KitchenService.domain.ticket.TicketLineItem;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class TicketLineItemOnDB {
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "rmenu_item_id")
    private String menuItemId;

    @Column(name = "name")
    private String name;

    public TicketLineItemOnDB() {}

    public TicketLineItemOnDB(String menuItemId, String name, int quantity) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.quantity = quantity;
    }

    public TicketLineItem transformNotEmbeddable() {
        return TicketLineItem.create(menuItemId, name, quantity);
    }
}
