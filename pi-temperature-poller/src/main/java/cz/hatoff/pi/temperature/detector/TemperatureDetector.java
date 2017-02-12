package cz.hatoff.pi.temperature.detector;

import com.pi4j.component.temperature.TemperatureSensor;
import cz.hatoff.pi.temperature.model.TemperatureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TemperatureDetector {

    @Autowired
    private TemperatureSensor temperatureSensor;

    public TemperatureInfo detectTemperature() {
        TemperatureInfo temperatureInfo = new TemperatureInfo();
        temperatureInfo.setDate(new Date());
        temperatureInfo.setTemperature(temperatureSensor.getTemperature());
        return temperatureInfo;
    }
}
