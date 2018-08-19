package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.Course;

public interface CourseRepository extends MongoRepository<Course, String> {

}
