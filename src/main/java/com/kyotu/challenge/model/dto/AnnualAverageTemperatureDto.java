package com.kyotu.challenge.model.dto;

import com.kyotu.challenge.model.AnnualAverageTemperature;

public record AnnualAverageTemperatureDto(String year, double averageTemperature) {
    public static AnnualAverageTemperatureDto of(AnnualAverageTemperature annualAverageTemperature) {
        return new AnnualAverageTemperatureDto(
                annualAverageTemperature.getYear(),
                annualAverageTemperature.getAverageTemperature()
        );
    }
}
