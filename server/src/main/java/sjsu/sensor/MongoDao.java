package sjsu.sensor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;



public class MongoDao {

    private static MongoDao instance = null;
    private static Object mutex = new Object();
    private static ApplicationContext ctx;
    private static MongoOperations mongoOperation;

    //singleton
    private MongoDao() {
        ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    }

    public static MongoDao getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) instance = new MongoDao();
            }
        }
        return instance;
    }

    public boolean delete(String id){

        Query q = new Query(Criteria.where("id").regex(id));
        mongoOperation.remove(q,TemplateModel.class);

        return true;
    }

    public void save(TemplateModel t){

        mongoOperation.save(t);

    }

    public void save(PhysicalSensorModel p){
        mongoOperation.save(p);
    }

    public TemplateModel findById(String id){
        Query q = new Query(Criteria.where("id").regex(id));
        return (TemplateModel)mongoOperation.findById(id, TemplateModel.class);
    }

    public AdminModel findUserById(String id){
        Query q = new Query(Criteria.where("id").regex(id));
        return (AdminModel)mongoOperation.findById(id, AdminModel.class);
    }

    public void saveUser(AdminModel admin){
        mongoOperation.save(admin);
    }
    public boolean updateOrInsert(OurTemplate data) {

        try {
            mongoOperation.save(data);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TemplateModel> getAllTemplates(){
        return mongoOperation.findAll(TemplateModel.class);
    }

    //customise
    public List<OurTemplate> getTemplate(String id){
        Query q = new Query(Criteria.where("proximity").exists(true));
        List<OurTemplate> temp = mongoOperation.find(q, OurTemplate.class);
        return temp;
    }

    public boolean deleteTemp(OurTemplate template){
        mongoOperation.remove(template);
        return true;
    }

    //customise
    public OurTemplate getOneTemplate(String id){

        Query q = new Query(Criteria.where("proximity").exists(true).and("location").regex("mumbai"));
        OurTemplate temp = mongoOperation.findOne(q, OurTemplate.class);

        return temp;
    }

/*
    public static void main(String[] args) {


        MongoDao mpo    =  MongoDao.getInstance();



        ///mpo.updateOrInsert(new OurTemplate("e","t","w"));


        OurTemplate t = mpo.getOneTemplate("");
        //t.setLocation("mumbai3");


        //mpo.updateOrInsert(t);
        System.out.println(t);
        mpo.deleteTemp(t);

    }*/
}
