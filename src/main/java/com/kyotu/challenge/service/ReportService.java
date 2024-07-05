package com.kyotu.challenge.service;

import com.kyotu.challenge.exception.ReportServiceException;
import com.kyotu.challenge.model.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    public List<AnnualAverageTemperature> calculateAnnualAverages(InputStream content, String city) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(content))) {
            return reader.lines()
                    .map(this::parseRecord)
                    .filter(record -> record != null && record.getCity().equals(city))
                    .collect(Collectors.groupingBy(
                            record -> String.valueOf(record.getTimestamp().getYear()),
                            Collectors.averagingDouble(TemperatureRecord::getTemperature)))
                    .entrySet().stream()
                    .map(entry -> new AnnualAverageTemperature(entry.getKey(), round(entry.getValue())))
                    .sorted(Comparator.comparing(AnnualAverageTemperature::getYear))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ReportServiceException("Error reading input stream", e);
        }
    }

    private TemperatureRecord parseRecord(String line) {
        try {
            return new TemperatureRecord(line);
        } catch (DateTimeParseException | NumberFormatException e) {
            System.err.println("Error parsing record: " + line + ". Error: " + e.getMessage());
            return null;
        }
    }

    private double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}