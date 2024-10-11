package modelo.repositorios;

import java.util.List;
import java.util.Optional;

import modelo.dominio.Persona;

public class RepositorioLista<T extends Keyable<S>, S> implements Repository<T, S> {
	private List<T> elementos;
	
	public RepositorioLista(Populable<T> populable) {
		super();
		this.setElementos(populable.getElementos());
	}

	
	@Override
	public boolean add(T objeto) {
		return getElementos().add(objeto);
	}

	@Override
	public Optional<T> getByKey(S key) {
		return getElementos().stream()
				.filter((elemento) 
						-> {
							return elemento.equalKey(key);})
				.findFirst();
	}

	@Override
	public boolean update(T objeto) {
		if (getElementos().remove(objeto)) {
			return getElementos().add(objeto);
		}
		return false;
	}

	@Override
	public Optional<T> delete(S key) {
		Optional<T> byKey = getByKey(key);
		if (byKey.isPresent()) {
			getElementos().remove(byKey.get());
			return byKey;
		}
		return Optional.empty();
	}


	public List<T> getElementos() {
		return elementos;
	}


	public void setElementos(List<T> elementos) {
		this.elementos = elementos;
	}

}
