package kz.sensor.app.repositories;

import jakarta.validation.constraints.NotEmpty;
import kz.sensor.app.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(@NotEmpty(message = "name cannot be empty") String name);

}
