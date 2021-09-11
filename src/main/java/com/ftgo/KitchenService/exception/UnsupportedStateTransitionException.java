package com.ftgo.KitchenService.exception;

import com.ftgo.KitchenService.domain.ticket.TicketState;

/**
 * Exception meaning unsupported state.
 */

public class UnsupportedStateTransitionException extends RuntimeException {
    public UnsupportedStateTransitionException(TicketState state) {
        super(state.name());
    }
}