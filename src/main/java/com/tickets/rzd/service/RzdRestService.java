package com.tickets.rzd.service;

import com.google.gson.*;
import com.tickets.rzd.dto.TicketsDTO;
import com.tickets.rzd.utils.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class RzdRestService {
    private Logger logger = LoggerFactory.getLogger(RzdRestService.class);

    private final String URI_TICKETS = "https://pass.rzd.ru/timetable/public/ru?layer_id=5827";

    @Autowired
    private ApplicationContext context;
    private RestTemplate restTemplate;

    @Autowired
    public RzdRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getTest(int period){
        Date dateNow = new Date();
        return dateNow.getListDateByPeriod(period);
    }

    public List<TicketsDTO> getTicketsByPeriod(int period) {
        Executor executor = Executors.newFixedThreadPool(100);
        Date dateNow = new Date();
        List<CompletableFuture<List<TicketsDTO>>> listCompletableFuture = new ArrayList<>();
        List<String> dateList = dateNow.getListDateByPeriod(period);

        for (String date : dateList){
            listCompletableFuture.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return getTickets(date).orElse(null);
                } catch (InterruptedException e) {
                    logger.error("GET TICKETS BY DATE ERROR", e);
                }
                return null;
            }, executor));
        }

        return listCompletableFuture.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public Optional<List<TicketsDTO>> getTickets(String date) throws InterruptedException {
        logger.info("TICKETS BY PERIOD: date {}", date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // DATE FORMAT EXAMPLE "24.03.2020"
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("dir","0");
        body.add("tfl","3");
        body.add("code0","2000000");
        body.add("code1","2060001");
        body.add("dt0", date);
        body.add("checkSeats","1");
        body.add("md","0");
        body.add("version","2");
        body.add("actorType","desktop_2016");

        Optional<JsonObject> response = wok(headers, body);
        if (response.isPresent()) {
            return toDTO(response.get());
        }

        return Optional.empty();
    }

    private Optional<List<TicketsDTO>> toDTO(JsonObject response) {
        Gson gson = new GsonBuilder().create();
        List<TicketsDTO> tickets = new ArrayList<>();

        try {
            JsonArray ticketsList = response.get("tp")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject()
                    .get("list")
                    .getAsJsonArray();

            logger.info("Response tickets |  tickets size: {}", ticketsList.size());
            for (int i = 0; i < ticketsList.size(); i++) {
                tickets.add(gson.fromJson(ticketsList.get(i).toString(), TicketsDTO.class));
            }
        } catch (Exception e) {
            logger.error("ERROR PARSE TICKETS JSON", e);
            return Optional.empty();
        }

        return Optional.of(tickets);
    }

    private Optional<JsonObject> wok(HttpHeaders headers, MultiValueMap<String, String> body) throws InterruptedException {
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(URI_TICKETS, HttpMethod.POST, entity, String.class);

        HttpHeaders responseHeaders = response.getHeaders();
        JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();
        String result = responseBody.get("result").getAsString();


        if (result.equals("OK")){
            return Optional.ofNullable(responseBody);
        } else if (result.equals("RID")) {
            Thread.sleep(1000);
            Map<String, String> uniqCookies = cookieParser(responseHeaders.get(HttpHeaders.SET_COOKIE));
            String cookies = uniqCookies.entrySet()
                    .stream()
                    .map(e -> e.getKey()+"="+e.getValue())
                    .collect(Collectors.joining("; "));

            String rid = responseBody.get("RID").getAsString();
            setRidInBody(body, rid);
            setCookie(headers, cookies);
            return wok(headers, body);
        }
        return Optional.empty();
    }

    private void setRidInBody(MultiValueMap<String, String> map, String rid) {
        map.put("rid", Collections.singletonList(rid));
    }

    private void setCookie(HttpHeaders headers, String cookies) {
        if (!headers.containsKey(HttpHeaders.COOKIE)){
            headers.add(HttpHeaders.COOKIE, cookies);
        }
    }

    private Map<String, String> cookieParser(List<String> cookies) {
        return  cookies.stream()
                .map(rawCookie -> {
                    String[] cookieLines = rawCookie.split(";");
                    String[] cookieParam = cookieLines[0].split("=");
                    return cookieParam;
                }).collect(Collectors.toMap(p -> p[0], p -> p[1], (x1, x2) -> x1));
    }
}
