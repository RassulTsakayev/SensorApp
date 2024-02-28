package kz.sensor.app.repositories;

import kz.sensor.app.models.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
    Optional<Measurements>findBySensor_id(int id);

    @Query("SELECT e FROM Measurements e WHERE e.raining = true")
    List<Measurements> getRainyDays();
}
