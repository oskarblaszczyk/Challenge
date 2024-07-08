package com.kyotu.challenge.model;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class AnnualTemperatureData {
    private double totalTemperature;
    private int count;

    public void addTemperature(double temperature) {
        totalTemperature += temperature;
        count++;
    }

    public double getAverageTemperature() {
        return round(count == 0 ? 0 : totalTemperature / count);
    }


    private static double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
