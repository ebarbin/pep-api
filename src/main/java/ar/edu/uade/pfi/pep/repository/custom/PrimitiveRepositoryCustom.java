package ar.edu.uade.pfi.pep.repository.custom;

import java.util.List;

import ar.edu.uade.pfi.pep.repository.document.Primitive;

public interface PrimitiveRepositoryCustom {

	public List<Primitive> findByNameLike(String nameSearch);
}
