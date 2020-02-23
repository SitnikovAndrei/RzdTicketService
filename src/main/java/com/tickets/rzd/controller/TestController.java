package com.tickets.rzd.controller;

import com.tickets.rzd.dto.TicketsDTO;
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
    private RzdRestService rzdRestService;

    @GetMapping("/test/{period}")
    public List<TicketsDTO> getTest(@PathVariable int period) throws Exception{
        return rzdRestService.getTicketsByPeriod(period);
    }

    @GetMapping(value = "/tickets/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTickets(@PathVariable String date) throws Exception{
        return ResponseEntity.ok(rzdRestService.getTickets(date));
    }

}
