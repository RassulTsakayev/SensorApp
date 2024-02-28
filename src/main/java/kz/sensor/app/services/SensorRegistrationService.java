package kz.sensor.app.services;

import jakarta.transaction.Transactional;
import kz.sensor.app.repositories.SensorRepository;
import kz.sensor.app.models.Sensor;
import org.springframework.stereotype.Service;

@Service
public class SensorRegistrationService {
    private final SensorService sensorService;
    private final SensorRepository sensorRepository;

    public SensorRegistrationService(SensorService sensorService, SensorRepository sensorRepository) {
        this.sensorService = sensorService;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void register(Sensor sensor){
        if(sensorService.loadSensorByName(sensor.getName()).isPresent()){
            return;
        }
        sensorRepository.save(sensor);
    }
}
