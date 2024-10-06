package modelo.repositorios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import index.CreateMap;
import modelo.dominio.Persona;

public class RepositorioLista<T extends Keyable<S>, S> implements Repository<T, S> {
	protected List<T> elementos;
	private File archive;
	private File indexMap;
	private Map<S, CreateMap> offSet;
	
	public RepositorioLista(Populable<T> populable) {
		super();
		this.elementos = populable.getElementos();
		this.archive = archive;
		this.indexMap = indexMap;
		offSet = new HashMap<S, CreateMap>();
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
public boolean store() {
		
		FileOutputStream grabador;
		RandomAccessFile serializadorAleatorioLector;
		ObjectOutputStream serializadorInicialGrabador;
		
		try {
			grabador= new FileOutputStream(indexMap, true);
			serializadorInicialGrabador = new ObjectOutputStream(grabador);
			
			// grabar
			RandomAccessFile randomAccessFile = new RandomAccessFile(indexMap, "rw");
			elementos.forEach((t) 
					->{ 
						try {
							long position = randomAccessFile.length();
							randomAccessFile.seek(position);
							offSet.put(t.getKey(), new CreateMap(position, true));
							serializadorInicialGrabador.writeObject(t);
							serializadorInicialGrabador.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
			grabador.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean load() {
		FileOutputStream grabador;
		RandomAccessFile serializadorAleatorioLector;
		
			offSet.entrySet().forEach(i -> {
				try {
				
					RandomAccessFile randomAccessFile=new RandomAccessFile(indexMap, "rw");
					randomAccessFile.seek(i.getValue().getKey());
					
					FileInputStream lector = new FileInputStream(randomAccessFile.getFD());
					ObjectInputStream serializadorLector = new ObjectInputStream(lector);
					
					Object object = serializadorLector.readObject();
					elementos.add((T) object);
					
					serializadorLector.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}				
			});
			
		return !elementos.isEmpty();
	}

}
