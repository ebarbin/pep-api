package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.RecipeRepository;
import ar.edu.uade.pfi.pep.repository.document.Recipe;

@Component
public class RecipeService implements Service<Recipe, Integer>{

	@Autowired
	private RecipeRepository recipeRepo;
	
	public List<Recipe> findAll() {
		return this.recipeRepo.findAll();
	}
	
	public Recipe save(Recipe recipe) {
		return this.recipeRepo.save(recipe);
	}
	
	public void delete(Integer id) {
		Recipe r = new Recipe();
		r.setId(id);
		this.recipeRepo.delete(r);
	}
}
