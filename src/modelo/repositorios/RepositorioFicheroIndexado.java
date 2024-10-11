package modelo.repositorios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import exceptions.IndexNotAccesibleException;
import exceptions.NotFolderPath;

public class RepositorioFicheroIndexado<T extends KeyAccesible<S>, S> implements Repository<T, S> {

	private String pathFolder;
	private String indexFile, objectFile;
	private Map<S, Long> index;
	private AccesibleUnicoObjeto<Map<S, Long>> accesoUnicoObjeto;
	private AccesibleMultiObjeto<T> accesoMultiObjeto;

	public RepositorioFicheroIndexado(String pathFolder, AccesibleMultiObjeto<T> accesoMultiObjeto)
			throws NotFolderPath, IndexNotAccesibleException {
		super();
		this.pathFolder = pathFolder;
		checkPath(pathFolder);
		createPaths();
		if (!loadIndex())
			throw new IndexNotAccesibleException();
		this.accesoMultiObjeto = accesoMultiObjeto;
		this.accesoMultiObjeto.setPath(objectFile);
	}

	private void createPaths() {
		indexFile = "./" + pathFolder + "/index.data";
		objectFile = "./" + pathFolder + "/objects.data";
	}

	private boolean loadIndex() {
		try {
			accesoUnicoObjeto = new AccesoFicheroSerializadoUnicoObjeto<Map<S, Long>>(indexFile);
			index = accesoUnicoObjeto.load().orElse(new HashMap<>());
			if (index.size() == 0)
				accesoUnicoObjeto.save(index);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void checkPath(String pathFolder) throws NotFolderPath {
		File folder = new File(pathFolder);
		// si existe y es un directorio
		boolean exists = folder.exists();
		if (exists && !folder.isDirectory())
			throw new NotFolderPath();
		if (!exists) {
			folder.mkdir();
		}
	}

	@Override
	public boolean add(T objeto) {
		if (index.containsKey(objeto.getKey()))
			return false;
		Long save = accesoMultiObjeto.save(objeto);
		if (save == -1) {
			return undoingChanges();
		}
		index.put(objeto.getKey(), save);
		try {
			accesoUnicoObjeto.save(index);
		} catch (Exception e) {
			index.remove(objeto.getKey());
			return undoingChanges();
		}
		return true;
	}

	private boolean undoingChanges() {
		accesoMultiObjeto.undo();
		return false;
	}

	@Override
	public Optional<T> getByKey(S key) {
		Long long1 = index.get(key);
		if (long1 != null) {
			return accesoMultiObjeto.load(long1);
		}
		return Optional.empty();
	}

	@Override
	public boolean update(T objeto) {
		Optional<T> delete = delete(objeto.getKey());
		if(delete.isPresent()) {
			return add(objeto);
		}
		return false;
	}

	@Override
	public Optional<T> delete(S key) {
		Optional<T> byKey = getByKey(key);
		if(byKey.isPresent()) {
			index.remove(key);
			return byKey;
		}
		return Optional.empty();
	}

}
