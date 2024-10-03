package modelo.repositorios.om;

import java.util.ArrayList;
import java.util.List;

import modelo.dominio.Persona;
import modelo.repositorios.Populable;

public class PersonaMother implements Populable<Persona> {

    // M�todo para obtener una lista de 15 personas
    public static List<Persona> crearVariasPersonas() {
        List<Persona> personas = new ArrayList<>();

        personas.add(new Persona("12345678A", "Juan P�rez"));
        personas.add(new Persona("23456789B", "Mar�a L�pez"));
        personas.add(new Persona("34567890C", "Carlos Garc�a"));
        personas.add(new Persona("45678901D", "Ana Gonz�lez"));
        personas.add(new Persona("56789012E", "Pedro Mart�nez"));
        personas.add(new Persona("67890123F", "Luc�a Fern�ndez"));
        personas.add(new Persona("78901234G", "Jos� Rodr�guez"));
        personas.add(new Persona("89012345H", "Sara S�nchez"));
        personas.add(new Persona("90123456I", "David Ram�rez"));
        personas.add(new Persona("01234567J", "Laura Herrera"));
        personas.add(new Persona("11111111K", "Luis Torres"));
        personas.add(new Persona("22222222L", "Carmen D�az"));
        personas.add(new Persona("33333333M", "Jorge Ruiz"));
        personas.add(new Persona("44444444N", "Sof�a Morales"));
        personas.add(new Persona("55555555O", "Manuel Ortega"));

        return personas;
    }

	@Override
	public List<Persona> getElementos() {
		
		return crearVariasPersonas();
	}
}
