package modelo.repositorios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class AccesoFicheroSerializadoUnicoObjeto<T> implements AccesibleUnicoObjeto<T> {

	private String path;
	
	public AccesoFicheroSerializadoUnicoObjeto(String path) {
		super();
		this.path = path;
	}

	@Override
	public Optional<T> load() throws IOException, ClassNotFoundException  {
		ObjectInputStream objectInputStream;
		File file = null;
		Object object = null;
		try {
			file= new File(path);
			objectInputStream = new ObjectInputStream(new FileInputStream(file));
			object = objectInputStream.readObject();
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			return Optional.empty();
		}
		return (Optional<T>) Optional.ofNullable(object);
	}
	
	@Override
	public void save(T t) throws FileNotFoundException, IOException {
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(new File(path)));
		objectOutputStream.writeObject(t);
		objectOutputStream.close();
	}
}
