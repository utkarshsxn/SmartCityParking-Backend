package sjsu.sensor;


public class OurTemplate {
    String id;
    String proximity;
    String temprature;
    String location;

    public OurTemplate(String proximity, String temprature, String location) {
        this.proximity = proximity;
        this.temprature = temprature;
        this.location = location;
    }

    public String getProximity() {
        return proximity;
    }

    public void setProximity(String proximity) {
        this.proximity = proximity;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "OurTemplate{" +
                "id='" + id + '\'' +
                ", proximity='" + proximity + '\'' +
                ", temprature='" + temprature + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
