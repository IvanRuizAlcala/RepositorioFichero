package test;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import modelo.dominio.Articulo;
import modelo.dominio.Persona;
import modelo.repositorios.RepositorioLista;
import modelo.repositorios.om.PersonaMother;

class ArtiucloTest {

	@Test
	void test() {
//		RepositorioLista<Articulo,Long> articuloRepositorioLista=new RepositorioLista<Articulo, Long>();
//		RepositorioLista<Persona,String> personaRepositorioLista=new RepositorioLista();
		RepositorioLista<Persona,String> personaRepositorioListaOM=new RepositorioLista(new PersonaMother());
		assertNotNull(personaRepositorioListaOM.getByKey("12345678A").get());
	}

}
