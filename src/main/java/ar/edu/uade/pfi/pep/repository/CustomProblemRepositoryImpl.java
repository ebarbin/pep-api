package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.Problem;

public class CustomProblemRepositoryImpl implements CustomProblemRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RequestDataHolder requestDataHolder;
	
	@Override
	public List<Problem> findByNameLike(String text) {
		Criteria c = Criteria.where("name").regex(text, "i");
		c.and("instituteId").is(this.requestDataHolder.getInstituteId());
		c.and("userId").is(this.requestDataHolder.getUserId());
		
		return this.mongoTemplate.find(new Query(c), Problem.class);
	}

}
