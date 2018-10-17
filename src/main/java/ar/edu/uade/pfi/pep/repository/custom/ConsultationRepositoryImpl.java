package ar.edu.uade.pfi.pep.repository.custom;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.Consultation;

@Repository
public class ConsultationRepositoryImpl implements ConsultationRespositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Override
	public List<Consultation> getTeacherConsultations() {

		Criteria c = Criteria.where("teacher.user._id").is(new ObjectId(this.requestDataHolder.getUserId()));
		c.and("teacherResponse").exists(false).and("deleted").is(false);

		Query q = new Query(c);
		q.with(new Sort(Direction.DESC, "creationDate"));

		return this.mongoTemplate.find(q, Consultation.class);
	}

	@Override
	public List<Consultation> getStudentConsultations() {

		Criteria c = Criteria.where("student.user._id").is(new ObjectId(this.requestDataHolder.getUserId()));
		c.and("deleted").is(false);
		Query q = new Query(c);
		q.with(new Sort(Direction.DESC, "creationDate"));

		return this.mongoTemplate.find(q, Consultation.class);
	}
	
	@Override
	public Long getStudentUnreadedResponses() {

		Criteria c = Criteria.where("student.user._id").is(new ObjectId(this.requestDataHolder.getUserId()));
		c.and("deleted").is(false).and("wasReadedByTeacher").is(true).and("wasReadedByStudent").is(false);
		c.and("teacherResponse").exists(true);
		
		Query q = new Query(c);
		q.with(new Sort(Direction.DESC, "creationDate"));

		return this.mongoTemplate.count(new Query(c), Consultation.class);
	}
	
	@Override
	public Long getTeacherUnreadedConsultations() {

		Criteria c = Criteria.where("teacher.user._id").is(new ObjectId(this.requestDataHolder.getUserId()));
		c.and("deleted").is(false).and("wasReadedByTeacher").is(false);
		
		Query q = new Query(c);
		q.with(new Sort(Direction.DESC, "creationDate"));

		return this.mongoTemplate.count(new Query(c), Consultation.class);
	}
}
