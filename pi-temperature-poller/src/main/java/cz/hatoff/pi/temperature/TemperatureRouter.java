package cz.hatoff.pi.temperature;


import cz.hatoff.pi.temperature.detector.TemperatureDetector;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemperatureRouter extends FatJarRouter {

    @Autowired
    private TemperatureDetector temperatureDetector;

    public static void main(String[] args) {
        SpringApplication.run(TemperatureRouter.class, args);
    }

    @Override
    public void configure() throws Exception {
        from("timer://measureTemperature?period=1m").routeId("temperatureRoute")
                .bean(temperatureDetector)
                .marshal().json(JsonLibrary.Jackson)
                .log("${body}")
                .to("activemq:queue:home.temperature");
    }


}
