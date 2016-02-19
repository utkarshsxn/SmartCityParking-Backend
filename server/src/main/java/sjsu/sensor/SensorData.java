package sjsu.sensor;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class SensorData {

    @Id
    String id;
    String location;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date lastAccessed;
    String dataType;
    String dataScale;
    String value;

    public SensorData(String location, Date lastAccessed, String dataType, String dataScale, String value) {
        //this.id = id;
        this.location = location;
        this.lastAccessed = lastAccessed;
        this.dataType = dataType;
        this.dataScale = dataScale;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataScale() {
        return dataScale;
    }

    public void setDataScale(String dataScale) {
        this.dataScale = dataScale;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{id: " + id
                + ",location: " + location
                + ",LastAcc: " + lastAccessed
                + ",DataType: " + dataType
                + ",DataScale: " + dataScale
                + ",value: " + value
                + "}";
    }
}
