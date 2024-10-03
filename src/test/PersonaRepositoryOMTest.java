package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.dominio.Persona;
import modelo.repositorios.om.PersonaMother;
import modelo.repositorios.om.PersonaRepositoryOM;

class PersonaRepositoryOMTest {
	List<Persona> personas = PersonaMother.crearVariasPersonas();
	PersonaRepositoryOM personaRepositoryOM = new PersonaRepositoryOM();

	@Test
	void testAdd() {
//		fail("Not yet implemented");
	}

	@Test
	void testGetByKey() {
//		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		Persona persona = personas.get(0);
		persona.setNombre("Emiliano Fuentes");
		personaRepositoryOM.update(persona);
		assertEquals(personaRepositoryOM.getByKey(persona.getdNICliente())
				.get()
				.getNombre(), persona.getNombre());
	}

	@Test
	void testDelete() {
//		fail("Not yet implemented");
	}

}
