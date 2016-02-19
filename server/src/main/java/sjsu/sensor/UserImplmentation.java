package sjsu.sensor;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableMongoRepositories
public class UserImplmentation implements User{

    MongoDao mongo = MongoDao.getInstance();
    private AdminModel admin = new AdminModel();

    @Override
    public AdminModel getUser(String id){
        AdminModel admin = mongo.findUserById(id);
        return admin;
    }

    @Override
    public void createUser(AdminModel admin){
        mongo.saveUser(admin);
    }
}
