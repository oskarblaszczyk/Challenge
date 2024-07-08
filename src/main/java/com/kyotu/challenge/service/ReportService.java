package com.kyotu.challenge.service;

import com.kyotu.challenge.exception.CityNotFoundException;
import com.kyotu.challenge.exception.DateParseException;
import com.kyotu.challenge.exception.FIleReadException;
import com.kyotu.challenge.exception.TemperatureParseException;
import com.kyotu.challenge.model.AnnualAverageTemperature;
import com.kyotu.challenge.model.AnnualTemperatureData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public List<AnnualAverageTemperature> calculateAnnualAverages(InputStream content, String city) {
        Map<Integer, AnnualTemperatureData> temperatureDataMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(content))) {
            reader.lines()
                    .map(line -> line.split(";"))
                    .filter(parts -> parts.length == 3 && parts[0].equalsIgnoreCase(city))
                    .forEach(line -> {
                        int year = parseDate(line[1]).getYear();
                        double temperature = parseTemperature(line[2]);
                        temperatureDataMap
                                .computeIfAbsent(year, _ -> new AnnualTemperatureData())
                                .addTemperature(temperature);
                    });
        } catch (IOException e) {
            throw new FIleReadException(e.getMessage());
        }


        return Optional.of(temperatureDataMap.entrySet().stream()
                        .map(e -> new AnnualAverageTemperature(String.valueOf(e.getKey()), e.getValue().getAverageTemperature()))
                        .sorted(Comparator.comparing(AnnualAverageTemperature::getYear))
                        .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new CityNotFoundException("City " + city + " not found in the provided data."));
    }

    private static double parseTemperature(String temperature) {
        try {
            return Double.parseDouble(temperature);
        } catch (Exception e) {
            throw new TemperatureParseException(e.getMessage());
        }
    }

    private static LocalDateTime parseDate(String timestamp) {
        try {
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } catch (Exception e) {
            throw new DateParseException(e.getMessage());
        }
    }


}