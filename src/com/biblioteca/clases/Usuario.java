package com.biblioteca.clases;

public class Usuario {
public String DNI;
public String nombre;
public boolean es_socio;

public Usuario(String DNI, String nombre,boolean es_socio) {
	this.DNI = DNI;
	this.nombre = nombre;
	this.es_socio=es_socio;
}
public String getDNI() {
	return DNI;
}
public void setDNI(String dNI) {
	DNI = dNI;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}


}
