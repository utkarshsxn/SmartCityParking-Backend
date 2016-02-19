package sjsu.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arpitkhare on 11/30/15.
 */
@Repository
@EnableMongoRepositories
public class TemplateImplementation implements Template {

    private TemplateModel template = new TemplateModel();
    MongoDao mongo = MongoDao.getInstance();

    @Override
    public List<TemplateModel> getAllTemplates(){
        return mongo.getAllTemplates();
    }

    @Override
    public TemplateModel getTemplate(String id){
        return mongo.findById(id);
    }

    @Override
    public void createTemplate(String id, ArrayList<String> dataType) {
        template = new TemplateModel(id,dataType);
        mongo.save(template);

    }

    @Override
    public  void deleteTemplate(String id){
        mongo.delete(id);

        // remove template code
    }

    @Override
    public void modifyTemplate(String id, ArrayList<String> newDataType){
        TemplateModel temp = mongo.findById(id);
        temp.setSensorDataType(newDataType);
        mongo.save(temp);
    }


}
