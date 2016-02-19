package sjsu.sensor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by arpitkhare on 12/7/15.
 */
public interface PhysicalSensor {
    public String createTemplate(String sensor_id, String location,String dataType,String activeStatus);

    String removeTemplate(String sensor_id,String status);

    ArrayList<HashMap<String,String>> allSensorRecords();

    String deleteSensor(String sensor_id);

    String addApplication(String app_name,String template, String app_id);

    ArrayList<HashMap<String,String>> allApplicationRecords();

    String removeApplication(String app_id, String status);

    String deleteApplication(String app_id);

    ArrayList<HashMap<String,String>> getSensorData(String app_id);
}
