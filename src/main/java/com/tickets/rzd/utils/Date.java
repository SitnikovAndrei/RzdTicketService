package com.tickets.rzd.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Date {
    private LocalDate localDate;

    public Date() {
        this.localDate = LocalDate.now();
    }

    public List<String> getListDateByPeriod(int period) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < period + 1; i++){
            list.add(this.localDate.plusDays(i).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        return list;
    }
}
