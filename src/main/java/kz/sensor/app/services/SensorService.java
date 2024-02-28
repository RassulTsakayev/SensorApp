package kz.sensor.app.services;


import kz.sensor.app.repositories.SensorRepository;
import kz.sensor.app.models.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository=sensorRepository;
    }

    @Transactional
    public Optional<Sensor> loadSensorByName(String name){
        return sensorRepository.findByName(name);
    }
}
