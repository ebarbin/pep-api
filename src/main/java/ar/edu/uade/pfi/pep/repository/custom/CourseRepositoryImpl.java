package ar.edu.uade.pfi.pep.repository.custom;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.Course;

@Repository
public class CourseRepositoryImpl implements CourseRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Override
	public List<Course> findForTeacherByNameLike(String nameSearch) {
		Criteria c = Criteria.where("name").regex(nameSearch, "i");
		c.and("teacher.user.id").is(new ObjectId(this.requestDataHolder.getUserId()));

		return this.mongoTemplate.find(new Query(c), Course.class);
	}

	@Override
	public List<Course> findForStudentByNameLike(String nameSearch) {
		Criteria c = Criteria.where("name").regex(nameSearch, "i");
		c.and("teacher.instituteId").is(this.requestDataHolder.getInstituteId());

		return this.mongoTemplate.find(new Query(c), Course.class);
	}
}
