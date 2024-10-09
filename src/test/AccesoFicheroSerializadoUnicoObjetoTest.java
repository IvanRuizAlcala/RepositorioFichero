package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import modelo.repositorios.AccesibleUnicoObjeto;
import modelo.repositorios.AccesoFicheroSerializadoUnicoObjeto;

class AccesoFicheroSerializadoUnicoObjetoTest {
	AccesibleUnicoObjeto<Map<String, Long>> accesoUnicoObjeto;
	String path="./mapa";
	
	@Test
	void testLoad()  {
		Map<String,Long> mapa;
		try {
			accesoUnicoObjeto = new AccesoFicheroSerializadoUnicoObjeto<Map<String,Long>>(path);
			mapa=accesoUnicoObjeto.load().get();
		} catch (NoSuchElementException e) {
			try {
				accesoUnicoObjeto.save(new HashMap<String,Long>());
				try {
					mapa=accesoUnicoObjeto.load().get();
					assertNotNull(mapa);
					assertEquals(0,mapa.size());
					long value = 1L;
					String key = "a";
					mapa.put(key, value);
					accesoUnicoObjeto.save(mapa);
					mapa=new HashMap<>();
					assertEquals(0,mapa.size());
					mapa=accesoUnicoObjeto.load().get();
					assertEquals(1, mapa.size());
					assertEquals(mapa.get(key), value);
					
				} catch (ClassCastException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new File(path).delete();
	}

}
