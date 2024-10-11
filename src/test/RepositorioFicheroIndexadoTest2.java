package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.IndexNotAccesibleException;
import exceptions.NotFolderPath;
import modelo.dominio.Articulo;
import modelo.repositorios.AccesoAleatorioFicheroSerializadoMultiObjeto;
import modelo.repositorios.AccesoSecuencialFicheroSerializadoMultiObjeto;
import modelo.repositorios.RepositorioFicheroIndexado;
import modelo.repositorios.Repository;

class RepositorioFicheroIndexadoTest2 {
	
	Repository<Articulo,Long> repositorio;
	String pathFolder="articulos";
	Articulo objeto = new Articulo(4000L, "Arandela", 4f);
	Articulo articulo2 = new Articulo(4001l,"colibri",1f);
	Articulo noExiste=new Articulo(4002L, "Tornillo Clavo", 1f);
	
	@BeforeEach
	void before() {
		try {
			repositorio=
					new RepositorioFicheroIndexado<Articulo,Long>(pathFolder
							,new AccesoSecuencialFicheroSerializadoMultiObjeto<Articulo>());
			Repository<Articulo,Long> repositorioFicheroIndexadoDos=new RepositorioFicheroIndexado<Articulo, Long>(pathFolder
					,new AccesoAleatorioFicheroSerializadoMultiObjeto<Articulo>());
		} catch (NotFolderPath e) {
			e.printStackTrace();
		} catch (IndexNotAccesibleException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAdd() {
		assertTrue(repositorio.add(objeto));
		assertFalse(repositorio.add(objeto));
	}

	@Test
	void testGetByKey() {
		insertTwoElements();
		assertTrue(repositorio.getByKey(objeto.getKey()).isPresent());
		assertTrue(repositorio.getByKey(articulo2.getKey()).isPresent());
	}

	private void insertTwoElements() {
		repositorio.add(articulo2);
		repositorio.add(objeto);
	}

	@Test
	void testUpdate() {
		insertTwoElements();
		Articulo articulo = repositorio.getByKey(objeto.getKey()).get();
		String descripcion = "cambio nombre";
		articulo.setDescripcion(descripcion);
		assertTrue(repositorio.update(articulo));
		Optional<Articulo> byKey = repositorio.getByKey(objeto.getKey());
		assertTrue(byKey.isPresent());
		assertEquals(byKey.get().getDescripcion(),descripcion);
		assertFalse(repositorio.update(noExiste));
	}

	@Test
	void testDelete() {
		insertTwoElements();
		assertEquals(repositorio.delete(articulo2.getKey()).get(),articulo2);
		assertTrue(repositorio.getByKey(articulo2.getKey()).isEmpty());
		assertFalse(repositorio.delete(noExiste.getKey()).isPresent());
	}

	@AfterEach
	void after() {
		File file=new File(pathFolder);
		if(file.exists()&&file.isDirectory()) {
			Arrays.asList(file.listFiles()).forEach(actual->actual.delete());
			file.delete();
		}
	}
}
