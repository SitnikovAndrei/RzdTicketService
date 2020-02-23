package com.tickets.rzd.controller;

import com.tickets.rzd.entity.TicketEntity;
import com.tickets.rzd.repository.TicketsRepository;
import com.tickets.rzd.service.RzdRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private TicketsRepository repository;

    @Autowired
    private RzdRestService rzdRestService;

    @GetMapping("/tickets/update")
    public ResponseEntity<?> ticketsUpdate() throws Exception{
        repository.deleteAll();
        List<TicketEntity> ticketEntityList = rzdRestService.getTicketsByPeriod(30);
        repository.saveAll(ticketEntityList);
        return ResponseEntity.ok("TRUE");
    }

    @GetMapping("/tickets/period/{period}")
    public List<TicketEntity> getTicketsByPeriod(@PathVariable int period) throws Exception{
        return rzdRestService.getTicketsByPeriod(period);
    }

    @GetMapping(value = "/tickets/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTickets(@PathVariable String date) throws Exception{
        return ResponseEntity.ok(rzdRestService.getTickets(date));
    }

}
