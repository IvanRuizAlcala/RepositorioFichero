package modelo.repositorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface AccesibleUnicoObjeto<T> {

	Optional<T> load() throws IOException, ClassNotFoundException;

	void save(T t) throws FileNotFoundException, IOException;

}