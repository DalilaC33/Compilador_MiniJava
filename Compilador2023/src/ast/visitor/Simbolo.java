package ast.visitor;

import ast.Type;

//estructua para almacenar un objeto del programa, clase, metodo o variable
public class Simbolo {
	
	public String tipo;
	public Type tipoDato;
	public String nombre;
	public int linea;
	public String deClase;
	public String deMetodo;
	public int lineaClase;
	public int lineaMetodo;
	
	
	public Simbolo(String tipo, Type tipoDato,String nombre, int linea,String deClase,String deMetodo, int lineaClase, int lineaMetodo ) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.linea = linea;
		this.tipoDato = tipoDato;
		this.deClase = deClase;
		this.deMetodo = deMetodo;
		this.lineaClase = lineaClase;
		this.lineaMetodo = lineaMetodo;
	}
	
	
	
	

}
