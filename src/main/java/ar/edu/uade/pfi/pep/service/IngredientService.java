package ar.edu.uade.pfi.pep.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.IngredientRepository;
import ar.edu.uade.pfi.pep.repository.document.Ingredient;

@Component
public class IngredientService implements Service<Ingredient, BigInteger>{

	@Autowired
	private IngredientRepository repo;

	@Override
	public List<Ingredient> findAll() {
		return this.repo.findAll();
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		return this.repo.save(ingredient);
	}

	@Override
	public void delete(BigInteger id) {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(id);
		this.repo.delete(ingredient);
	}

	public List<Ingredient> save(List<Ingredient> ingredients) {
		return this.repo.saveAll(ingredients);
	}
}
