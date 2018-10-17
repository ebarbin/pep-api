package ar.edu.uade.pfi.pep.repository.custom;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.Problem;

@Repository
public class ProblemRepositoryImpl implements ProblemRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Override
	public List<Problem> findByNameLike(String text) {
		Criteria c = Criteria.where("name").regex(text, "i");
		c.and("teacher.user.id").is(new ObjectId(this.requestDataHolder.getUserId()));

		return this.mongoTemplate.find(new Query(c), Problem.class);
	}

}
