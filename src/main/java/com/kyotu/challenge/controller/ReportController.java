package com.kyotu.challenge.controller;

import com.kyotu.challenge.model.dto.AnnualAverageTemperatureDto;
import com.kyotu.challenge.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/challenge")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;


    @PostMapping(value = "/{city}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<AnnualAverageTemperatureDto>> getAnnualAverages(@RequestPart("file") MultipartFile file, @RequestParam String city) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<AnnualAverageTemperatureDto> report = reportService.calculateAnnualAverages(inputStream, city).stream()
                .map(AnnualAverageTemperatureDto::of)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }


}
