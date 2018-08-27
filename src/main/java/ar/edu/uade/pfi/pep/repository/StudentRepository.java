package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import ar.edu.uade.pfi.pep.repository.document.Student;

public interface StudentRepository extends MongoRepository<Student, String>, QueryByExampleExecutor<Student> {

}
