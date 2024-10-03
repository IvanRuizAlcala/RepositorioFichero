package modelo.repositorios;

import java.util.List;
import java.util.Optional;

import modelo.dominio.Persona;

public class RepositorioLista<T extends Keyable<S>, S> implements Repository<T, S> {
	protected List<T> elementos;
	
	public RepositorioLista(Populable<T> populable) {
		super();
		this.elementos = populable.getElementos();
	}

	
	@Override
	public boolean add(T objeto) {
		return elementos.add(objeto);
	}

	@Override
	public Optional<T> getByKey(S key) {
		return elementos.stream().filter((elemento) -> {
			return (elemento).equalKey(key);
		}).findFirst();
	}

	@Override
	public boolean update(T objeto) {
		if (elementos.remove(objeto)) {
			return elementos.add(objeto);
		}
		return false;
	}

	@Override
	public Optional<T> delete(S key) {
		Optional<T> byKey = getByKey(key);
		if (byKey.isPresent()) {
			elementos.remove(byKey.get());
			return byKey;
		}
		return Optional.empty();
	}

}
