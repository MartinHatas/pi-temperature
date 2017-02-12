package cz.hatoff.pi.temperature;


import cz.hatoff.pi.temperature.transformer.TemperatureTransformer;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.apache.camel.LoggingLevel.ERROR;

@SpringBootApplication
public class TemperatureStoreRouter extends FatJarRouter {

    @Autowired
    private TemperatureTransformer temperatureTransformer;

    @Override
    public void configure() throws Exception {

        onException(Throwable.class)
                .log(ERROR, "Failed to index data ${exception}")
                .rollback();

        from("activemq:queue:home.temperature?transacted=true").routeId("storeRoute")
                .unmarshal().json(JsonLibrary.Jackson)
                .bean(temperatureTransformer)
                .marshal().json(JsonLibrary.Jackson)
                .log("Going to index: ${body}")
                .setHeader(HTTP_METHOD, constant("POST"))
                .to("http://localhost:9200/temperatures/homeTemperature/")
                .log("Index response: ${body}");
    }

    @Bean(name = "activemq")
    public ActiveMQComponent activeMqComponent(){
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setBrokerURL("tcp://192.168.0.7:61616");
        return activeMQComponent;
    }

    @Bean
    public TemperatureTransformer temperatureTransformer(){
        return new TemperatureTransformer();
    }




}
