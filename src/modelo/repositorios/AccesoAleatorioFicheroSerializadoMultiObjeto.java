package modelo.repositorios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Optional;

public class AccesoAleatorioFicheroSerializadoMultiObjeto<T> implements AccesibleMultiObjeto<T> {

	private File file;
	private Long undoLong;

	public AccesoAleatorioFicheroSerializadoMultiObjeto() {
		super();
	}
	
	public AccesoAleatorioFicheroSerializadoMultiObjeto(String path) {
		super();
		setFile(path);
	}

	private void setFile(String path) {
		file=new File(path);
	}


	public void setPath(String path) {
		setFile(path);
	}
	
	@Override
	public Optional<T> load(Long offset) {
		try {
			if (file.exists()) {
				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
				randomAccessFile.seek(offset);
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(randomAccessFile.getFD()));
				Object object = objectInputStream.readObject();
				objectInputStream.close();
				randomAccessFile.close();
				return (Optional<T>) Optional.ofNullable(object);
				 
		        
			}
		} catch (ClassNotFoundException | IOException e) {
			
		}
		return Optional.empty();
	}
	
	@Override
	public Long save(T t) {
		long pos=-1;
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			pos = randomAccessFile.length();
			undoLong=pos;
			randomAccessFile.seek(pos);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file, true));
			objectOutputStream.writeObject(t);
			objectOutputStream.close();
			randomAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			pos=-1;
		}
		return pos;
	}

	@Override
	public void undo() {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			randomAccessFile.setLength(undoLong);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
