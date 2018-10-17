package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.document.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

	List<Course> findByProblemsId(String problemId);

	List<Course> findByTeacherInstituteId(String instituteId);

	List<Course> findByTeacherUserId(String userId);

	Course findByTeacherUserIdAndName(String userId, String name);
}
