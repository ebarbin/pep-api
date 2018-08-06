package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.Ingredient;

public interface IngredientRepository extends MongoRepository<Ingredient, Integer> {

}
