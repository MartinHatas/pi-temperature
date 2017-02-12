package cz.hatoff.pi.temperature.transformer;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TemperatureTransformer {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public Map<String, Object> transform(Map<String, Object> temperatureObject){
        Long timestamp = (Long) temperatureObject.get("date");
        Date date = new Date(timestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        temperatureObject.put("date", simpleDateFormat.format(date));
        return temperatureObject;
    }


}
