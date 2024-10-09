package modelo.dominio;

import modelo.repositorios.Keyable;

public class Persona implements Keyable<String> {
	private String dni;
	private String nombre;
//hhhh
	public Persona(String dNICliente, String nombre) {
		super();
		this.dni = dNICliente;
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 0
	 * 
	 * @Override public int hashCode() { return Object.hash(dni); }
	 **/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return obj.equals(dni);
	}

	@Override
	public boolean equalKey(String keyable) {
		return this.getDni().equals(keyable);
	}

	/**
	 * public String getKey() { // TODO Auto-generated method stub return null; }
	 */

}
