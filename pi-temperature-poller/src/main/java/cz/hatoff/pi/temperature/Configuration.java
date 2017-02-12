package cz.hatoff.pi.temperature;


import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;
import cz.hatoff.pi.temperature.detector.TemperatureDetector;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.context.annotation.Bean;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public TemperatureDetector temperatureDetector() {
        return new TemperatureDetector();
    }

    @Bean
    public TemperatureSensor temperatureSensor() {
        W1Master master = new W1Master();
        List<W1Device> w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
        if (w1Devices.isEmpty()) {
            throw new RuntimeException("No temperature sensor devices have been found.");
        }
        return (TemperatureSensor)w1Devices.get(0);
    }

    @Bean(name = "activemq")
    public ActiveMQComponent activeMqComponent(){
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setBrokerURL("tcp://localhost:61616");
        return activeMQComponent;
    }
}
