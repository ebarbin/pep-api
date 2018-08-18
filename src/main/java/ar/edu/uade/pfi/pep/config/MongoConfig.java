package ar.edu.uade.pfi.pep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}
	
	@Override
	public MongoClient mongoClient() {
		return new MongoClient("127.0.0.1");
	}

	@Override
	protected String getDatabaseName() {
		return "pfi";
	}

}
