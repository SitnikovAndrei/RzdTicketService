package com.tickets.rzd.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tickets.rzd.dto.ServiceCategoriesDTO;
import com.tickets.rzd.dto.TicketDTO;
import com.tickets.rzd.entity.TicketEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
class TicketsMapper {
    private Logger logger = LoggerFactory.getLogger(TicketsMapper.class);

    List<TicketDTO> toDTO(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().create();
        List<TicketDTO> tickets = new ArrayList<>();
        try {
            JsonArray ticketsList = jsonObject.get("tp")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject()
                    .get("list")
                    .getAsJsonArray();

            logger.info("Response tickets |  tickets size: {}", ticketsList.size());
            for (int i = 0; i < ticketsList.size(); i++) {
                tickets.add(gson.fromJson(ticketsList.get(i).toString(), TicketDTO.class));
            }
        } catch (Exception e) {
            logger.error("ERROR PARSE TICKETS JSON", e);
        }
        return tickets;
    }

    TicketEntity toEntity(TicketDTO ticketDTO) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setBrand(ticketDTO.getBrand());
        try {
            ticketEntity.setDateFrom(format.parse( ticketDTO.getDate0() + " " + ticketDTO.getTime0()));
            ticketEntity.setDateTo(format.parse( ticketDTO.getDate1() + " " + ticketDTO.getTime1()));
        } catch (ParseException e) {
            logger.error("ERROR PARSE DATE TicketDTO", e);
        }
        ticketEntity.setNumber(ticketDTO.getNumber());
        ticketEntity.setPrice(ticketDTO.getServiceCategories()
                .stream()
                .map(ServiceCategoriesDTO::getPrice)
                .mapToInt(Integer::parseInt)
                .min().orElse(10000));

        return ticketEntity;
    };
}
