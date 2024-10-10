package modelo.dominio;

import java.io.Serializable;

import modelo.repositorios.KeyAccesible;
import modelo.repositorios.Keyable;

public class Articulo implements KeyAccesible<Long>,Serializable  {
	private Long id;
	private String descripcion;
	private float precio;
	public Articulo(Long id, String descripcion, float precio) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.precio = precio;
	}
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	@Override
	public boolean equalKey(Long keyable) {
		return this.id.equals(keyable);
	}
	@Override
	public Long getKey() {
		// TODO Auto-generated method stub
		return this.getId();
	}

	
	
}
