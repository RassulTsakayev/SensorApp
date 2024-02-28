package kz.sensor.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "measurements")
public class Measurements {
    @ManyToOne()
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @JsonBackReference
    private Sensor sensor;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    private Double value;
    @Column(name = "raining")
    private boolean raining;

    public Measurements(Sensor sensor, Double value, boolean raining) {
        this.sensor = sensor;
        this.value = value;
        this.raining = raining;
    }

    public Measurements() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
