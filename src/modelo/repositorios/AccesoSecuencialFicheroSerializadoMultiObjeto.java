package modelo.repositorios;

import java.util.Optional;

public class AccesoSecuencialFicheroSerializadoMultiObjeto<T> implements AccesibleMultiObjeto<T> {

	@Override
	public Optional<T> load(Long offset) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Long save(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPath(String path) {
		// TODO Auto-generated method stub

	}
}
