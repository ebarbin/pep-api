package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import ar.edu.uade.pfi.pep.repository.document.Course;

public interface CourseRepository extends MongoRepository<Course, String>, QueryByExampleExecutor<Course> {

	List<Course> findByProblemsId(String problemId);

}
