package modelo.repositorios;

import java.util.Optional;

public interface AccesibleMultiObjeto<T> {

	Optional<T> load(Long offset);

	Long save(T t);

	void undo();

}