package ar.edu.uade.pfi.pep.repository.custom;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;

public class CustomConsultationRepositoryImpl implements CustomConsultationRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Override
	public Long getStudentUnreadedResponses() {
		Criteria c = Criteria.where("student.user.id").is(new ObjectId(this.requestDataHolder.getUserId()));
		c.and("wasReaded").is(true).and("teacherResponse.wasReaded").is(false);

		return this.mongoTemplate.count(new Query(c), Consultation.class);
	}

	@Override
	public Long getTeacherUnreadedConsultations() {
		Criteria c = Criteria.where("student.selectedCourse.teacher.user.id")
				.is(new ObjectId(this.requestDataHolder.getUserId()));
		c.and("wasReaded").is(false);

		return this.mongoTemplate.count(new Query(c), Consultation.class);
	}
}
