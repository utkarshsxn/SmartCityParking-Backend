package sjsu.sensor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyMongoRepo extends MongoRepository<TemplateModel,String > {
    public TemplateModel findById(String id);
}
