package sjsu.sensor;

/**
 * Created by arpitkhare on 11/15/15.
 */

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

    @Override
    public String getDatabaseName() {
        return "virsendb";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(""));//enter mongidb connection string
    }
}