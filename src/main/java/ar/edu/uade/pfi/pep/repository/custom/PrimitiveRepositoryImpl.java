package ar.edu.uade.pfi.pep.repository.custom;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.Primitive;

@Repository
public class PrimitiveRepositoryImpl implements PrimitiveRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RequestDataHolder requestDataHolder;
	
	@Override
	public List<Primitive> findByNameLike(String nameSearch) {
		Criteria c = Criteria.where("name").regex(nameSearch, "i");
		c.and("teacher.user.id").is(new ObjectId(this.requestDataHolder.getUserId()));

		return this.mongoTemplate.find(new Query(c), Primitive.class);
	}

}
