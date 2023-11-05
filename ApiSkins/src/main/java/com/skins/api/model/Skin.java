package com.skins.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity //Marcara la clase como una entidad de base de datos lo que hara que maneje mejor la persistente
public class Skin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String tipo;
	private double precio;
	private String color;
	
	public Skin(Long id, String nombre, String tipo, double precio, String color) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
		this.color = color;
	}
	
	public Skin() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
	

}
