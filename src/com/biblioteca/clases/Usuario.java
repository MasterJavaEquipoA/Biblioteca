package com.biblioteca.clases;

public class Usuario {
public String DNI;
public String nombre;
public Usuario(String DNI, String nombre) {
	this.DNI = DNI;
	this.nombre = nombre;
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
