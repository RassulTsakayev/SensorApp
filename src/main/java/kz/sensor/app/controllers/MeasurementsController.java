package kz.sensor.app.controllers;

import jakarta.validation.Valid;
import kz.sensor.app.models.Measurements;
import kz.sensor.app.services.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List; 

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private MeasurementsService measurementsService;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus>add(@RequestBody @Valid List <Measurements> measurements,
                                         BindingResult bindingResult){
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().build() : measurementsService.addMeasurement(measurements);
    }

    @GetMapping
    public ResponseEntity<String> findAll() {
        return measurementsService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<String>findRainyDays(){
        return measurementsService.findRainyDays();
    }
}
