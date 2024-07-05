package com.kyotu.challenge.model.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class TemperatureRecord{
    private String city;
    private LocalDateTime timestamp;
    private double temperature;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public TemperatureRecord(String line) {
        String[] args = line.split(";");
        this.city = args[0];
        this.timestamp = LocalDateTime.parse(args[1], formatter);
        this.temperature = Double.parseDouble(args[2]);
    }
}