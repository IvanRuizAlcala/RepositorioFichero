package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.dominio.Persona;
import modelo.repositorios.RepositorioLista;
import modelo.repositorios.om.PersonaMother;

class RepositorioListaTest {
	
	RepositorioLista<Persona,String> personaRepositorioListaOM;
	PersonaMother populable;
	@BeforeEach
	void before() {
		populable= new PersonaMother();
		personaRepositorioListaOM=new RepositorioLista<Persona,String>(populable);
		
	}

	@Test
	void testAdd() {
		int sizeAnterior = personaRepositorioListaOM.getElementos().size();
		Persona nuevaPersona = new Persona("888888","Ancleto");
		assertTrue(personaRepositorioListaOM.getByKey(nuevaPersona.getDni()).isEmpty());
		assertTrue(personaRepositorioListaOM.add(nuevaPersona));
		assertEquals(sizeAnterior+1,personaRepositorioListaOM.getElementos().size());
	}

	@Test
	void testGetByKey() {
		Persona persona = populable.getElementos().get(0);
		assertTrue(personaRepositorioListaOM.getByKey(persona.getDni()).isPresent());
		String keyNoExist="999999";
		assertTrue(personaRepositorioListaOM.getByKey(keyNoExist).isEmpty());
	}

	@Ignore
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Ignore
	void testDelete() {
		fail("Not yet implemented");
	}

}
