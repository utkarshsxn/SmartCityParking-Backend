package sjsu.sensor;

import java.util.ArrayList;

/**
 * Created by arpitkhare on 11/30/15.
 */
public class TemplateModel {

    private String id;
    private ArrayList<String> sensorDataType;

    public TemplateModel(String template_id, ArrayList<String> sensorDataType) {
        this.id = template_id;
        this.sensorDataType = sensorDataType;
    }


    public TemplateModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getSensorDataType() {
        return sensorDataType;
    }

    public void setSensorDataType(ArrayList<String> sensorDataType) {
        this.sensorDataType = sensorDataType;
    }
}
