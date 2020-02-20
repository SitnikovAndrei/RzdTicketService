package com.tickets.rzd.controller;

import com.tickets.rzd.entity.TicketEntity;
import com.tickets.rzd.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
public class TicketsController {

    @Autowired
    private TicketsRepository repository;

    @GetMapping("/tickets/list")
    public List<TicketEntity> findByDateFrom(@RequestParam String date) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date dateStart = format.parse(String.format("%s 00:00", date));
        Date dateEnd = format.parse(String.format("%s 23:59", date));

        return repository.findByDateFromBetween(dateStart, dateEnd);
    }

    @GetMapping("/tickets/id/{id}")
    public TicketEntity findById(@PathVariable Integer id) throws Exception{
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/tickets/all")
    public List<TicketEntity> findAll(){
        return repository.findAll();
    }
}
