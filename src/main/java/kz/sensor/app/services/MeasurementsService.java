package kz.sensor.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.sensor.app.models.Measurements;
import kz.sensor.app.models.Sensor;
import kz.sensor.app.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private MeasurementsRepository measurementsRepository;

    private SensorService sensorService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorService sensorService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorService = sensorService;
    }

    public void save(Measurements measurements) {
        Optional<Measurements> optionalMeasurements = measurementsRepository.findBySensor_id(measurements.getSensor().getId());

//        if (optionalMeasurements.isPresent()) {
//            Measurements toUpdate = optionalMeasurements.get();
//
//            // Update the property
//            toUpdate.setValue(measurements.getValue());
//            toUpdate.setRaining(measurements.isRaining());
//
//            // Save the updated entity
//            measurementsRepository.save(toUpdate);
//        }else{
//            measurementsRepository.save(measurements);
//        }
        measurementsRepository.save(measurements);
        // Handle the case when the entity with the given ID is not found
    }

    public ResponseEntity<String> findAll(){
        List<Measurements> list =  measurementsRepository.findAll();
        if(list.isEmpty()){
            throw new EmptyStackException();
        }
        try {
            // Convert the list to JSON string using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(list);

            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            // Handle the exception appropriately, maybe return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    public Optional<Measurements> findBySensorId(Integer id) {
        return measurementsRepository.findBySensor_id(id);
    }

    @Transactional
    public ResponseEntity<HttpStatus> addMeasurement(List<Measurements> measurements) {
        for(Measurements each: measurements){
            String name = each.getSensor().getName();

            Sensor sensor = sensorService.loadSensorByName(name).orElse(null);
            if(sensor!=null) {
                each.setSensor(sensor);
                save(each);
            }else{
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> findRainyDays() {
        List<Measurements>list = measurementsRepository.getRainyDays();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(list);
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing json");
        }
    }
}
