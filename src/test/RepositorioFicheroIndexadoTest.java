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
import modelo.repositorios.RepositorioFicheroIndexado;
import modelo.repositorios.Repository;

class RepositorioFicheroIndexadoTest {
	
	Repository<Articulo,Long> repositorioFicheroIndexado;
	String pathFolder="articulos";
	Articulo objeto = new Articulo(4000L, "Arandela", 4f);
	Articulo articulo2 = new Articulo(4001l,"colibri",1f);
	Articulo noExiste=new Articulo(4002L, "Tornillo Clavo", 1f);
	
	@BeforeEach
	void before() {
		try {
			repositorioFicheroIndexado=
					new RepositorioFicheroIndexado<Articulo,Long>(pathFolder);
		} catch (NotFolderPath e) {
			e.printStackTrace();
		} catch (IndexNotAccesibleException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	void testAdd() {
		assertTrue(repositorioFicheroIndexado.add(objeto));
		assertFalse(repositorioFicheroIndexado.add(objeto));
	}

	@Ignore
	void testGetByKey() {
		insertTwoElements();
		assertTrue(repositorioFicheroIndexado.getByKey(objeto.getKey()).isPresent());
		assertTrue(repositorioFicheroIndexado.getByKey(articulo2.getKey()).isPresent());
	}

	private void insertTwoElements() {
		repositorioFicheroIndexado.add(articulo2);
		repositorioFicheroIndexado.add(objeto);
	}

	@Test
	void testUpdate() {
		insertTwoElements();
		Articulo articulo = repositorioFicheroIndexado.getByKey(objeto.getKey()).get();
		String descripcion = "cambio nombre";
		articulo.setDescripcion(descripcion);
		boolean update = repositorioFicheroIndexado.update(articulo);
		assertTrue(update);
		Optional<Articulo> byKey = repositorioFicheroIndexado.getByKey(objeto.getKey());
		assertTrue(byKey.isPresent());
		String descripcion2 = byKey.get().getDescripcion();
		System.out.println(descripcion2);
		System.out.println(descripcion);
		assertEquals(descripcion2,descripcion);
		assertFalse(repositorioFicheroIndexado.update(noExiste));
	}

	@Test
	void testDelete() {
		insertTwoElements();
		assertEquals(repositorioFicheroIndexado.delete(articulo2.getKey()).get(),articulo2);
		assertTrue(repositorioFicheroIndexado.getByKey(articulo2.getKey()).isEmpty());
		assertFalse(repositorioFicheroIndexado.delete(noExiste.getKey()).isPresent());
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
