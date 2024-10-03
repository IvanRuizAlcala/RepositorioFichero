package modelo.repositorios;

import java.util.Optional;

public interface Repository<T,S> {
	// CRUD
	public boolean add(T objeto);

	public Optional<T> getByKey(S key);

	public boolean update(T objeto);

	public Optional<T> delete(S key);
}
