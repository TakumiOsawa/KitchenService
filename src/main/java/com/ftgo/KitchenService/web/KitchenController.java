package com.ftgo.KitchenService.web;

import com.ftgo.KitchenService.KitchenService;
import com.ftgo.KitchenService.domain.ticket.TicketAcceptance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;

@RestController
public class KitchenController {
    private final KitchenService kitchenService;

    public KitchenController(@Autowired KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping("/hcheck")
    @ResponseStatus(HttpStatus.OK)
    public HealthCheckResponse healthCheck() {
        return new HealthCheckResponse();
    }

    @PostMapping("/tickets/{ticketId}/accept")
    public void acceptTicket(@PathVariable long ticketId, @RequestBody TicketAcceptance ticketAcceptance) {
        kitchenService.accept(ticketId, ticketAcceptance.getReadyBy().atZone(ZoneId.systemDefault()));
    }
}
