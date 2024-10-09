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
	private AccesibleUnicoObjeto<Map<S, Long>> accesoSerializadoUnicoObjeto;
	private AccesibleMultiObjeto<T> accesoSerializadoAleatorioMultiObjeto;

	public RepositorioFicheroIndexado(String pathFolder) throws NotFolderPath, IndexNotAccesibleException {
		super();
		this.pathFolder = pathFolder;
		checkPath(pathFolder);
		createPaths();
		if (!loadIndex())
			throw new IndexNotAccesibleException();
		accesoSerializadoAleatorioMultiObjeto = new AccesoAleatorioFicheroSerializadoMultiObjeto<>(objectFile);
	}

	private void createPaths() {
		indexFile = "./" + pathFolder + "/index.data";
		objectFile = "./" + pathFolder + "/objects.data";
	}

	private boolean loadIndex() {
		try {
			accesoSerializadoUnicoObjeto = new AccesoFicheroSerializadoUnicoObjeto<Map<S, Long>>(indexFile);
			index = accesoSerializadoUnicoObjeto.load().orElse(new HashMap<>());
			if (index.size() == 0)
				accesoSerializadoUnicoObjeto.save(index);
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

	// S es la clase, T es tipo
	@Override
	public boolean add(T objeto) {
		S clave = objeto.getKey();
		if (index.containsKey(objeto.getKey())) {
			return false; // la clave ya existe
		}
		// uso el método save (retorna una posicion) que hay en serializado multiobjeto
		// para guaradr en el mapa
		Long posicion = accesoSerializadoAleatorioMultiObjeto.save(objeto);
		// meto en el mapa index la clave y la posicion
		index.put(clave, posicion);
		try {
			accesoSerializadoUnicoObjeto.save(index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public Optional<T> getByKey(S key) {
		if (!index.containsKey(key)) {
			return Optional.empty();
			// no encuentra clave
		}

		Long offset = index.get(key); // nos devuelve el offset a partir de la clave (indice)
		return accesoSerializadoAleatorioMultiObjeto.load(offset); // te devuelve el objeto a su posicion en el archivo
	}

	@Override
	public boolean update(T objeto) {
		S clave = objeto.getKey();
		if (!index.containsKey(clave)) {
			return false;
			// si no existe existe en el mapa, no hay objeto qeu actualizar
		}

		Long offset = index.get(clave);
		accesoSerializadoAleatorioMultiObjeto.save(objeto); // como es cambiar un objeto entero lo reescribimos
		index.put(clave, offset); // lo vuelvo a cargar en el map index
		try {
			accesoSerializadoUnicoObjeto.save(index); // lo guardo en el objeto unico
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Optional<T> delete(S key) {
		if (!index.containsKey(key)) {
			return Optional.empty();
		}
		Long offset = index.get(key);
		Optional<T> objeto = accesoSerializadoAleatorioMultiObjeto.load(offset); // cargamos el objeto en el archivo
		if (objeto.isPresent()) {
			index.remove(key); //si está presente que lo borre
		}
		try {
			accesoSerializadoUnicoObjeto.save(index);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // antes de borrarlo porqe el metodo
			// devuelve el archivo borrado
		return objeto;

	}

}
