package com.kyotu.challenge.service;

import com.kyotu.challenge.model.entity.AnnualAverageTemperature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void testCalculateAnnualAverages_withValidData() {
        String data = """
                Warszawa;2018-09-19 05:17:32.619;22.5
                Warszawa;2018-09-20 05:17:32.619;23.0
                Warszawa;2019-09-19 05:17:32.619;20.0
                Warszawa;2019-09-20 05:17:32.619;21.5""";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        String city = "Warszawa";

        List<AnnualAverageTemperature> result = reportService.calculateAnnualAverages(inputStream, city);

        assertEquals(2, result.size());
        assertEquals("2018", result.get(0).getYear());
        assertEquals(22.8, result.get(0).getAverageTemperature());
        assertEquals("2019", result.get(1).getYear());
        assertEquals(20.8, result.get(1).getAverageTemperature());
    }

    @Test
    public void testCalculateAnnualAverages_withDifferentCity() {
        String data = """
                Warszawa;2018-09-19 05:17:32.619;22.5
                Warszawa;2018-09-20 05:17:32.619;23.0
                Kraków;2019-09-19 05:17:32.619;20.0
                Kraków;2019-09-20 05:17:32.619;21.5""";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        String city = "Warszawa";

        List<AnnualAverageTemperature> result = reportService.calculateAnnualAverages(inputStream, city);

        assertEquals(1, result.size());
        assertEquals("2018", result.getFirst().getYear());
        assertEquals(22.8, result.getFirst().getAverageTemperature());
    }

    @Test
    public void testCalculateAnnualAverages_withEmptyData() {
        String data = "";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        String city = "Warszawa";

        List<AnnualAverageTemperature> result = reportService.calculateAnnualAverages(inputStream, city);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testCalculateAnnualAverages_withInvalidData() {
        String data = """
                Invalid;Data;Line
                Warszawa;2018-09-19 05:17:32.619;22.5
                Warszawa;2018-09-20 05:17:32.619;invalidTemp
                Warszawa;2019-09-20 05:17:32.619;21.5""";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        String city = "Warszawa";

        List<AnnualAverageTemperature> result = reportService.calculateAnnualAverages(inputStream, city);

        assertEquals(2, result.size());
        assertEquals("2018", result.get(0).getYear());
        assertEquals(22.5, result.get(0).getAverageTemperature());
        assertEquals("2019", result.get(1).getYear());
        assertEquals(21.5, result.get(1).getAverageTemperature());
    }
}