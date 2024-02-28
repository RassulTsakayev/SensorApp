package kz.sensor.app.controllers;

import jakarta.validation.Valid;
import kz.sensor.app.models.Sensor;
import kz.sensor.app.services.SensorRegistrationService;
import kz.sensor.app.util.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorValidator sensorValidator;
    private final SensorRegistrationService service;

    @Autowired
    public SensorController(SensorValidator sensorValidator,
                            SensorRegistrationService service) {
        this.sensorValidator = sensorValidator;
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus>register(@RequestBody @Valid Sensor sensor,
                                              BindingResult bindingResult){
        sensorValidator.validate(sensor, bindingResult);
        if(bindingResult.hasErrors()){
            for(ObjectError error: bindingResult.getAllErrors())
                System.out.println(error.getDefaultMessage());
            return ResponseEntity.badRequest().build();
        }
        service.register(sensor);

        return ResponseEntity.ok().build();
    }
}
