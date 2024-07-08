package com.kyotu.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnnualAverageTemperature {
    private String year;
    private double averageTemperature;
}