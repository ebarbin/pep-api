package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import ar.edu.uade.pfi.pep.repository.document.Teacher;

public interface TeacherRepository extends MongoRepository<Teacher, String>, QueryByExampleExecutor<Teacher> {

}
