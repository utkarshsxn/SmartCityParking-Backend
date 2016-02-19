package sjsu.sensor;


public interface User {

    public AdminModel getUser(String id);

    public void createUser(AdminModel admin);
}
