package ar.edu.uade.pfi.pep.service;

import java.util.List;

public interface Service<T, D> {

	public List<T> findAll();
	
	public T save(T t);
	
	public void delete(D id);
}
