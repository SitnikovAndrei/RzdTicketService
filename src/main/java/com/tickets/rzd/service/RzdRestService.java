package com.tickets.rzd.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tickets.rzd.utils.Date;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class RzdRestService {
    private RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//    private final String URI = "https://jsonplaceholder.typicode.com/todos/1";
    private final String URI_TICKETS = "https://pass.rzd.ru/timetable/public/ru?layer_id=5827";

    public List<String> getTest(int period){
        Date dateNow = new Date();
        return dateNow.getListDateByPeriod(period);
    }

    public List<String> getTicketsByPeriod(int period) throws ExecutionException, InterruptedException {
        Date dateNow = new Date();
        List<CompletableFuture<String>> listCompletableFuture = new ArrayList<>();
        List<String> dateList = dateNow.getListDateByPeriod(period);

        for (String date : dateList){
            listCompletableFuture.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return getTickets(date);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }));
        }

        CompletableFuture<String>[] arrayCompletableFuture = new CompletableFuture[listCompletableFuture.size()];
        listCompletableFuture.toArray(arrayCompletableFuture);

        CompletableFuture<Void> all = CompletableFuture.allOf(arrayCompletableFuture);
        all.get();

        return Arrays.stream(arrayCompletableFuture)
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public String getTickets(String date) throws InterruptedException {
        System.out.println("TICKETS BY PERIOD: " + date);
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

        JsonObject response = wok(headers, body);
        return response != null ? response.get("tp")
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("list")
                .getAsJsonArray()
                .toString() : null;
    }

    private JsonObject wok(HttpHeaders headers, MultiValueMap<String, String> body) throws InterruptedException {
        HttpEntity entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(URI_TICKETS, HttpMethod.POST, entity, String.class);

        HttpHeaders responseHeaders = response.getHeaders();
        JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();
        String result = responseBody.get("result").getAsString();

        if (result.equals("RID")) {
            Thread.sleep(500);
            Map<String, String> uniqCookies = cookieParser(responseHeaders.get(HttpHeaders.SET_COOKIE));
            String cookies = uniqCookies.entrySet()
                    .stream()
                    .map(e -> e.getKey()+"="+e.getValue())
                    .collect(Collectors.joining("; "));

            String rid = responseBody.get("RID").getAsString();
//            headers.put(HttpHeaders.COOKIE, cookies);
            setRidInBody(body, rid);
            setCookie(headers, cookies);
            return wok(headers, body);
        } else if (result.equals("OK")){
            return responseBody;
        }
        return null;
    }

    private void setRidInBody(MultiValueMap<String, String> map, String rid) {
//        System.out.println("map: " + map);
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
