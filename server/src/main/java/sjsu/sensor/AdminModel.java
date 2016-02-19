package sjsu.sensor;

public class AdminModel {
    private String id;
    private String pwd;

    public AdminModel() {
    }

    public AdminModel(String id, String pwd) {
        this.pwd = pwd;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String toString() {
        return "User: '" + this.id + "', Password: '" + this.pwd;
    }
}
